package com.example.dpp.model.api.order;

public class OrderProductList {
    private int productId;
    private int quantity;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderProductList() {
    }

    public OrderProductList(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
