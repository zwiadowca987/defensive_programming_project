package com.example.dpp.model.db.warehouses;

import com.example.dpp.model.db.ProductsList;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "warehouses")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "warehouse_name")
    private String name;

    @ElementCollection
    @Column(name = "products_list")
    private List<ProductsList> productsList;

    public Warehouse() {
    }

    public Warehouse(int id, String name, List<ProductsList> productsList) {
        this.id = id;
        this.name = name;
        this.productsList = productsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductsList> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<ProductsList> productsList) {
        this.productsList = productsList;
    }
}
