package com.example.dpp.services;

import com.example.dpp.model.api.warehouses.WarehouseCreation;
import com.example.dpp.model.api.warehouses.WarehouseInfo;
import com.example.dpp.model.api.warehouses.WarehouseProductInfo;
import com.example.dpp.model.db.warehouses.Warehouse;
import com.example.dpp.repository.ProductRepository;
import com.example.dpp.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WarehouseService implements IWarehouseService {

    public static final Function<Warehouse, WarehouseInfo> WAREHOUSE_TO_WAREHOUSE_INFO_FUNCTION = u -> {
        var info = new WarehouseInfo();
        info.setId(u.getId());
        info.setName(u.getName());
        return info;
    };
    private final WarehouseRepository repository;
    private final ProductRepository productRepository;

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository, ProductRepository productRepository) {
        this.repository = warehouseRepository;
        this.productRepository = productRepository;
    }

    @Override
    public WarehouseInfo getWarehouse(int warehouseId) {
        return repository.findById(warehouseId).map(WAREHOUSE_TO_WAREHOUSE_INFO_FUNCTION).orElse(null);
    }

    @Override
    public List<WarehouseInfo> getAllWarehouses() {
        return repository.findAll().stream().map(WAREHOUSE_TO_WAREHOUSE_INFO_FUNCTION).collect(Collectors.toList());
    }

    @Override
    public boolean addWarehouse(WarehouseCreation warehouseInfo) {
        var warehouse = new Warehouse();
        warehouse.setName(warehouseInfo.getWarehouseName());
        repository.save(warehouse);
        return true;
    }

    @Override
    public boolean updateWarehouse(int id, WarehouseCreation warehouse) {
        var oldWarehouse = repository.findById(id).orElse(null);
        if (oldWarehouse == null)
            throw new IllegalStateException("Warehouse not found");
        oldWarehouse.setName(warehouse.getWarehouseName());
        repository.save(oldWarehouse);
        return true;
    }

    @Override
    public boolean deleteWarehouse(int warehouseId) {
        repository.deleteById(warehouseId);
        return true;
    }

    @Override
    public List<WarehouseProductInfo> getProductsByWarehouseId(int warehouseId) {
        var warehouse = repository.findById(warehouseId).orElse(null);
        if (warehouse == null)
            throw new IllegalStateException("Warehouse not found");

        return warehouse
                .getProductsList()
                .stream()
                .filter(u -> u.getQuantity() > 0)
                .map(u -> {
                    var info = new WarehouseProductInfo();
                    info.setWarehouseId(warehouse.getId());
                    info.setWarehouseName(warehouse.getName());
                    info.setProductId(u.getProduct().getId());
                    info.setProductName(u.getProduct().getProductName());
                    info.setQuantity(u.getQuantity());
                    return info;
                }).toList();
    }

    @Override
    public boolean addProductToWarehouse(int warehouseId, int productId, int amount) {
        var warehouse = repository.findById(warehouseId).orElse(null);
        if (warehouse == null)
            throw new IllegalStateException("Warehouse not found");
        var product = productRepository.findById(productId).orElse(null);
        if (product == null)
            throw new IllegalStateException("Product not found");
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be greater than 0");
        warehouse.AddProductToWarehouse(product, amount);
        return true;
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }
}
