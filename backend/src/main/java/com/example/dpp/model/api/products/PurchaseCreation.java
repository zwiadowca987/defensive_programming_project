package com.example.dpp.model.api.products;

import com.example.dpp.model.PurchaseStatus;

import java.util.Date;
import java.util.List;

public class PurchaseCreation {
    private int clientId;
    private Date date;
    private double price;
    private PurchaseStatus status;
    private List<PurchaseProductInfo> products;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
