package com.example.dpp.model.api.warehouses;

import com.example.dpp.model.api.AddressInfo;
import com.example.dpp.model.db.warehouses.Warehouse;

public class WarehouseInfo {

    private Integer id;

    private String name;

    private AddressInfo address;

    public WarehouseInfo() {
    }

    public WarehouseInfo(Integer id, String name, AddressInfo address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public WarehouseInfo(Warehouse warehouse) {
        this.id = warehouse.getId();
        this.name = warehouse.getName();
        this.address = new AddressInfo(warehouse.getAddress());
    }

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
