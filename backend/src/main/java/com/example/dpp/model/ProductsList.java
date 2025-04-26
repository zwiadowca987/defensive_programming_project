package com.example.dpp.model;

public class ProductsList {
    private Integer ProductId;
    private Integer Quantity;

    public ProductsList() {
    }

    public ProductsList(Integer productId, Integer quantity) {
        ProductId = productId;
        Quantity = quantity;
    }

    public Integer getProductId() {
        return ProductId;
    }

    public void setProductId(Integer productId) {
        ProductId = productId;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }
}
