package com.example.dpp.model.db.warehouses;

import com.example.dpp.model.db.Address;
import com.example.dpp.model.db.products.Product;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "warehouses")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "warehouse_name", unique = true, nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(name = "warehouse_products", joinColumns = @JoinColumn(name = "warehouse_id"))
    private List<ProductsList> productsList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

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

    public void setId(Integer id) {
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

    public void AddProductToWarehouse(Product product, int amount) {
        var product_ = productsList.stream().filter(u -> u.getProduct().getId() == product.getId()).findFirst().orElse(null);
        if (product_ != null) {
            product_.setQuantity(product_.getQuantity() + amount);
        }
        else {
            productsList.add(new ProductsList(product, amount));
        }
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
