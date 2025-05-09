package com.example.dpp.model.api.products;

public class AddProduct {
    private String productName;
    private double price;
    private String description;
    private String producer;

    public AddProduct() {
    }

    public AddProduct( String productName, double price, String description, String producer) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
