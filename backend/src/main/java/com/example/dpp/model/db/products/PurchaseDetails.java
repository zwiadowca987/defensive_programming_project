package com.example.dpp.model.db.products;

import com.example.dpp.model.api.products.PurchaseInfo;
import com.example.dpp.model.api.products.PurchaseProductInfo;
import jakarta.persistence.*;

@Entity
public class PurchaseDetails {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id", nullable = false)
    private Purchase purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id", nullable = false)
    private Product product;

    @Column(name = "quantity")
    private int quantity;
    @Id
    private Long id;

    public PurchaseDetails() {
    }

    public PurchaseDetails(Purchase purchase, Product product, int quantity) {
        this.purchase = purchase;
        this.product = product;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PurchaseProductInfo convertToPurchaseProductInfo(){
        var purchaseProductInfo = new PurchaseProductInfo();
        purchaseProductInfo.setProductName(product.getProductName());
        purchaseProductInfo.setPrice(product.getPrice() * quantity);
        purchaseProductInfo.setQuantity(quantity);
        purchaseProductInfo.setProductId(product.getId());
        return purchaseProductInfo;
    }
}
