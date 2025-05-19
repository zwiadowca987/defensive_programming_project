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
        this.ProductId = productId;
        this.Quantity = quantity;
    }

    public Integer getProductId() {
        return ProductId;
    }

    public void setProductId(Integer productId) {
        this.ProductId = productId;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        this.Quantity = quantity;
    }
}
