package com.example.dpp.model.api.products;

import com.example.dpp.model.PurchaseStatus;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseInfo {

    private int id;
    private int clientId;
    private LocalDateTime date;
    private double price;
    private PurchaseStatus status;
    private List<PurchaseProductInfo> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public PurchaseStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseStatus status) {
        this.status = status;
    }

    public List<PurchaseProductInfo> getProducts() {
        return products;
    }

    public void setProducts(List<PurchaseProductInfo> products) {
        this.products = products;
    }

    public void addProduct(PurchaseProductInfo product) {
        this.products.add(product);
    }
}
