package com.example.dpp.model.db.warehouses;

import com.example.dpp.model.db.products.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Embeddable
@Table(name = "products_lists")
public class ProductsList {

    @ManyToOne
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    public ProductsList() {
    }

    public ProductsList(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
