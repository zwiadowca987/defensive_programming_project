package com.example.dpp.model.api.warehouses;

import com.example.dpp.model.api.Address;

public class WarehouseCreation {
    private String warehouseName;
    private Address address;

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
