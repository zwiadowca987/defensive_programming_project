package com.example.dpp.model.products;

import jakarta.persistence.*;

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
    private double price;

    @Column(name = "description")
    private String description;

    @Column(name = "producer")
    private String producer;

    public Product() {
    }

    public Product(int id, String productName, double price, String description, String producer) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.producer = producer;
    }

    public Product(Product product) {
        this.id = product.id;
        this.productName = product.productName;
        this.price = product.price;
        this.description = product.description;
        this.producer = product.producer;
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