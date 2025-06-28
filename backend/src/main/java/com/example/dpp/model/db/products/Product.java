package com.example.dpp.model.db.products;

import com.example.dpp.model.api.products.ProductInfo;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @Column(name = "producer")
    private String producer;

    @OneToMany(mappedBy = "product")
    private Set<PurchaseDetails> purchaseInfos;

    public Product() {
    }

    public Product(int id, String productName, BigDecimal price, String description, String producer) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.producer = producer;
        this.purchaseInfos = new HashSet<>();
    }

    public Product(Product product) {
        this.id = product.id;
        this.productName = product.productName;
        this.price = product.price;
        this.description = product.description;
        this.producer = product.producer;
        this.purchaseInfos = new HashSet<>(product.purchaseInfos);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public ProductInfo convertToProductInfo() {
        var product = new ProductInfo();
        product.setId(id);
        product.setProductName(productName);
        product.setPrice(price);
        product.setDescription(description);
        product.setProducer(producer);
        return product;
    }

    public Set<PurchaseDetails> getPurchaseInfos() {
        return purchaseInfos;
    }

    public void setPurchaseInfos(Set<PurchaseDetails> purchaseInfos) {
        this.purchaseInfos = purchaseInfos;
    }

    public void addPurchaseDetails(PurchaseDetails purchaseDetails) {
        this.purchaseInfos.add(purchaseDetails);
    }
}