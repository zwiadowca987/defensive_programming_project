package com.example.dpp.model.api.warehouses;

import com.example.dpp.model.api.Address;

public class WarehouseInfo {

    private Integer id;

    private String name;

    private Address address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
