package com.example.dpp.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;

@Embeddable
@Table(name = "products_lists")
public class ProductsList {
    @Column(name = "product_id")
    private int productId;

    @Column(name = "quantity")
    private int quantity;

    public ProductsList() {
    }

    public ProductsList(Integer productId, Integer quantity) {
        this.productId = productId;
        this.productId = quantity;
    }

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
