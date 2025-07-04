package com.example.dpp.model.api.warehouses;

import com.example.dpp.model.api.AddressInfo;

public class WarehouseCreation {
    private String warehouseName;
    private AddressInfo address;

    public WarehouseCreation(String warehouseName, AddressInfo address) {
        this.warehouseName = warehouseName;
        this.address = address;
    }

    public WarehouseCreation() {
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public AddressInfo getAddress() {
        return address;
    }

    public void setAddress(AddressInfo address) {
        this.address = address;
    }
}
