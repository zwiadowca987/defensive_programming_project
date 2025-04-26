package com.example.dpp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Embeddable
@Table(name = "products_lists")
public class ProductsList {
    @Column(name = "product_id")
    private Integer ProductId;

    @Column(name = "quantity")
    private int Quantity;

    public ProductsList() {
    }

    public ProductsList(int productId, int quantity) {
        ProductId = productId;
        Quantity = quantity;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
