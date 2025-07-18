package com.example.dpp.model.db.products;

import com.example.dpp.model.api.products.PurchaseProductInfo;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class PurchaseDetails {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity")
    private int quantity;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public PurchaseProductInfo convertToPurchaseProductInfo() {
        if(product == null)
            return null;

        var purchaseProductInfo = new PurchaseProductInfo();


        purchaseProductInfo.setProductName(product.getProductName());
        purchaseProductInfo.setPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        purchaseProductInfo.setQuantity(quantity);
        purchaseProductInfo.setProductId(product.getId());
        return purchaseProductInfo;
    }
}
