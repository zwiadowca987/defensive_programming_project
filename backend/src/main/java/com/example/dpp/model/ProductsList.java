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

    public ProductsList(Integer productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
