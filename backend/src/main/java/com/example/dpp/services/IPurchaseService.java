package com.example.dpp.services;

import com.example.dpp.model.api.products.NewPurchase;
import com.example.dpp.model.api.products.PurchaseInfo;

import java.util.List;

public interface IPurchaseService {
    List<PurchaseInfo> getPurchases();

    PurchaseInfo getPurchase(int id);

    PurchaseInfo createPurchase(NewPurchase purchaseInfo);

    boolean deletePurchase(int id);

    boolean updatePurchase(int id, NewPurchase purchaseInfo);

    boolean addProduct(int purchaseId, int productId, int quantity);

    boolean existsById(Integer id);
}
