package com.example.dpp.model.api.warehouses;

import com.example.dpp.model.api.AddressInfo;

public class WarehouseInfo {

    private Integer id;

    private String name;

    private AddressInfo address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AddressInfo getAddress() {
        return address;
    }

    public void setAddress(AddressInfo address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
