package com.example.dpp.services;

import com.example.dpp.model.api.products.PurchaseCreation;
import com.example.dpp.model.api.products.PurchaseInfo;
import com.example.dpp.model.db.products.Purchase;
import com.example.dpp.model.db.products.PurchaseDetails;
import com.example.dpp.repository.ProductRepository;
import com.example.dpp.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService implements IPurchaseService {

    private final PurchaseRepository repository;
    private final ProductRepository productRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, ProductRepository productRepository) {
        this.repository = purchaseRepository;
        this.productRepository = productRepository;
    }


    @Override
    public List<PurchaseInfo> getPurchases() {
        return repository.findAll().stream().map(Purchase::convertToPurchaseInfo).collect(Collectors.toList());
    }

    @Override
    public PurchaseInfo getPurchase(int id) {
        var purchase = repository.findById(id).orElse(null);
        return (purchase != null) ? purchase.convertToPurchaseInfo() : null;
    }

    @Override
    public PurchaseInfo createPurchase(PurchaseCreation purchaseInfo) {
        var purchase = new Purchase();
        purchase.setDate(purchaseInfo.getDate());
        purchase.setClientId(purchaseInfo.getClientId());
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
        var purchase = repository.findById(id).orElse(null);
        if (purchase == null) {
            return false;
        }
        purchase.setDate(purchaseInfo.getDate());
        purchase.setClientId(purchaseInfo.getClientId());
        purchase.setStatus(purchaseInfo.getStatus());
        repository.save(purchase);
        return true;
    }

    @Override
    public boolean addProduct(int purchaseId, int productId, int quantity) {
        var product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return false;
        }
        var purchase = repository.findById(purchaseId).orElse(null);
        if (purchase == null) {
            return false;
        }
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
