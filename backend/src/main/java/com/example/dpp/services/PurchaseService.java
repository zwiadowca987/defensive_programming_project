package com.example.dpp.services;

import com.example.dpp.model.PurchaseStatus;
import com.example.dpp.model.api.products.PurchaseCreation;
import com.example.dpp.model.api.products.PurchaseInfo;

import java.util.List;

public interface PurchaseService {
    List<PurchaseInfo> getPurchases();

    List<PurchaseInfo> getPurchasesByCustomerId(int id);

    PurchaseInfo getPurchase(int id);

    PurchaseInfo createPurchase(PurchaseCreation purchaseInfo);

    boolean deletePurchase(int id);

    boolean updatePurchase(int id, PurchaseCreation purchaseInfo);

    PurchaseInfo addProduct(int purchaseId, int productId, int quantity);

    void removeProduct(int purchaseId, int productId);

    PurchaseInfo updateProductQuantity(int purchaseId, int productId, int quantity);

    boolean existsById(Integer id);

    PurchaseInfo updateStatus(int id, PurchaseStatus status);
}
