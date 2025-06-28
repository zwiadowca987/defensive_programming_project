package com.example.dpp.model.api.products;

import java.math.BigDecimal;

public class ProductCreation {
    private String productName;
    private BigDecimal price;
    private String description;
    private String producer;

    public ProductCreation() {
    }

    public ProductCreation(String productName, BigDecimal price, String description, String producer) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.producer = producer;
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
}
