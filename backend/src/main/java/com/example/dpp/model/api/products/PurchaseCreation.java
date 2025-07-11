package com.example.dpp.model.api.products;

import com.example.dpp.model.PurchaseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PurchaseCreation {
    private int clientId;
    private LocalDateTime date;
    private BigDecimal price;
    private PurchaseStatus status;
    private List<PurchaseProductInfo> products;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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
