package com.example.dpp.services;

import com.example.dpp.model.api.warehouses.WarehouseCreation;
import com.example.dpp.model.api.warehouses.WarehouseInfo;
import com.example.dpp.model.api.warehouses.WarehouseProductInfo;

import java.util.List;

public interface WarehouseService {
    WarehouseInfo getWarehouse(int warehouseId);

    List<WarehouseInfo> getAllWarehouses();

    boolean createWarehouse(WarehouseCreation warehouseInfo);

    boolean updateWarehouse(int id, WarehouseCreation warehouse);

    boolean deleteWarehouse(int warehouseId);

    List<WarehouseProductInfo> getProductsByWarehouseId(int warehouseId);

    boolean addProductToWarehouse(int warehouseId, int productId, int amount);

    boolean existsById(Integer id);

    List<WarehouseProductInfo> getProductsInWarehouses();

    WarehouseInfo getWarehouseByName(String name);
}
