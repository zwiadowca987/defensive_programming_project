package com.example.dpp.services;

import com.example.dpp.model.api.products.PurchaseCreation;
import com.example.dpp.model.api.products.PurchaseInfo;
import com.example.dpp.model.db.products.Purchase;
import com.example.dpp.model.db.products.PurchaseDetails;
import com.example.dpp.repository.CustomerRepository;
import com.example.dpp.repository.ProductRepository;
import com.example.dpp.repository.PurchaseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService implements IPurchaseService {

    private final PurchaseRepository repository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository,
                           ProductRepository productRepository,
                           CustomerRepository customerRepository) {
        this.repository = purchaseRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public List<PurchaseInfo> getPurchases() {
        return repository.findAll().stream().map(Purchase::convertToPurchaseInfo).collect(Collectors.toList());
    }

    @Override
    public List<PurchaseInfo> getPurchasesByCustomerId(int id) {
        var customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        return customer.getPurchases().stream().map(Purchase::convertToPurchaseInfo).collect(Collectors.toList());
    }

    @Override
    public PurchaseInfo getPurchase(int id) {
        var purchase = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Purchase with id " + id + " not found"));
        return purchase.convertToPurchaseInfo();
    }

    @Override
    public PurchaseInfo createPurchase(PurchaseCreation purchaseInfo) {
        var purchase = new Purchase();
        purchase.setDate(purchaseInfo.getDate());
        purchase.setCustomer(
                customerRepository
                        .findById(purchaseInfo.getClientId())
                        .orElseThrow(() -> new IllegalArgumentException("Customer not found")));
        purchase.setStatus(purchaseInfo.getStatus());

        repository.save(purchase);
        return purchase.convertToPurchaseInfo();
    }

    @Override
    public boolean deletePurchase(int id) {
        repository.deleteById(id);
        return true;
    }

    @Override
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
    public boolean addProduct(int purchaseId, int productId, int quantity) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));;

        var purchase = repository.findById(purchaseId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase with id " + purchaseId + " not found"));;

        var details = new PurchaseDetails();
        details.setPurchase(purchase);
        details.setQuantity(quantity);
        details.setProduct(product);

        purchase.addPurchaseDetails(details);
        repository.save(purchase);
        return true;
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }
}
