package com.example.dpp.services;

import com.example.dpp.model.PurchaseStatus;
import com.example.dpp.model.api.products.PurchaseCreation;
import com.example.dpp.model.api.products.PurchaseInfo;
import com.example.dpp.model.db.products.Purchase;
import com.example.dpp.model.db.products.PurchaseDetails;
import com.example.dpp.repository.CustomerRepository;
import com.example.dpp.repository.ProductRepository;
import com.example.dpp.repository.PurchaseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.metamodel.mapping.ordering.ast.OrderByComplianceViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository repository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository,
                               ProductRepository productRepository,
                               CustomerRepository customerRepository) {
        this.repository = purchaseRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    @Transactional
    public List<PurchaseInfo> getPurchases() {
        return repository.findAll().stream().map(Purchase::convertToPurchaseInfo).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<PurchaseInfo> getPurchasesByCustomerId(int id) {
        var customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        return customer.getPurchases().stream().map(Purchase::convertToPurchaseInfo).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PurchaseInfo getPurchase(int id) {
        var purchase = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Purchase with id " + id + " not found"));
        return purchase.convertToPurchaseInfo();
    }

    @Override
    @Transactional
    public PurchaseInfo createPurchase(PurchaseCreation purchaseInfo) {
        var purchase = new Purchase();
        purchase.setDate(purchaseInfo.getDate() == null ? LocalDateTime.now() : purchaseInfo.getDate() );
        purchase.setCustomer(
                customerRepository
                        .findById(purchaseInfo.getClientId())
                        .orElseThrow(() -> new IllegalArgumentException("Customer not found")));
        purchase.setStatus(purchaseInfo.getStatus());

        repository.save(purchase);
        return purchase.convertToPurchaseInfo();
    }

    @Override
    @Transactional
    public boolean deletePurchase(int id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean updatePurchase(int id, PurchaseCreation purchaseInfo) {
        var purchase = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Purchase with id " + id + " not found"));

        purchase.setDate(purchaseInfo.getDate());
        purchase.setCustomer(
                customerRepository
                        .findById(purchaseInfo.getClientId())
                        .orElseThrow(() -> new EntityNotFoundException("Client with id " + purchaseInfo.getClientId() + " not found")));
        purchase.setStatus(purchaseInfo.getStatus());
        repository.save(purchase);
        return true;
    }

    @Override
    @Transactional
    public PurchaseInfo addProduct(int purchaseId, int productId, int quantity) {

        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));


        var purchase = repository.findById(purchaseId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase with id " + purchaseId + " not found"));

        if(purchase.getStatus() != PurchaseStatus.PLACED){
            throw new OrderByComplianceViolation("Order wth status " + purchase.getStatus() + "can not be modified");
        }

        var entity = purchase.getPurchaseInfos().stream().filter(u -> u.getId() == productId).findFirst().orElse(null);

        if(entity == null) {
            var details = new PurchaseDetails();
            details.setPurchase(purchase);
            details.setQuantity(quantity);
            details.setProduct(product);

            purchase.addPurchaseDetails(details);
            repository.save(purchase);
        }
        else {
            entity.setQuantity(quantity);
            repository.save(purchase);
        }

        return purchase.convertToPurchaseInfo();
    }

    @Override
    @Transactional
    public void removeProduct(int purchaseId, int productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

        var purchase = repository.findById(purchaseId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase with id " + purchaseId + " not found"));

        var entity = purchase.getPurchaseInfos().stream().filter(u -> u.getId() == productId).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " is not a part of the order with id " + purchaseId));

        if(purchase.getStatus() != PurchaseStatus.PLACED){
            throw new OrderByComplianceViolation("Order wth status " + purchase.getStatus() + "can not be modified");
        }

        purchase.getPurchaseInfos().remove(entity);
        repository.save(purchase);
    }


    @Override
    @Transactional
    public PurchaseInfo updateProductQuantity(int purchaseId, int productId, int quantity) {

        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

        var purchase = repository.findById(purchaseId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase with id " + purchaseId + " not found"));

        if(purchase.getStatus() != PurchaseStatus.PLACED){
            throw new OrderByComplianceViolation("Order wth status " + purchase.getStatus() + "can not be modified");
        }

        var entity = purchase.getPurchaseInfos().stream().filter(u -> u.getId() == productId).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " is not a part of the order with id " + purchaseId));

        entity.setQuantity(quantity);
        repository.save(purchase);

        return purchase.convertToPurchaseInfo();
    }

    @Override
    @Transactional
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional
    public PurchaseInfo updateStatus(int id, PurchaseStatus status) {
        var purchase = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Purchase with id " + id + " not found"));

        purchase.setStatus(status);
        repository.save(purchase);
        return purchase.convertToPurchaseInfo();

    }
}
