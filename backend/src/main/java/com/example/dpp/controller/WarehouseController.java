package com.example.dpp.controller;

import com.example.dpp.model.api.warehouses.WarehouseCreation;
import com.example.dpp.model.api.warehouses.WarehouseInfo;
import com.example.dpp.model.api.warehouses.WarehouseProductInfo;
import com.example.dpp.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@CrossOrigin(origins = "http://localhost:3000")
public class WarehouseController {
    public static final ResponseStatusException WAREHOUSE_NOT_FOUND = new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
    private final WarehouseService service;

    @Autowired
    public WarehouseController(WarehouseService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<WarehouseInfo> findAll() {
        return service.getAllWarehouses();
    }

    @GetMapping("/{id}")
    public WarehouseInfo findById(@PathVariable Integer id) {
        var warehouse = service.getWarehouse(id);
        if (warehouse == null) {
            throw WAREHOUSE_NOT_FOUND;
        }
        return warehouse;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@RequestBody WarehouseCreation warehouse) {
        service.createWarehouse(warehouse);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody WarehouseCreation warehouse, @PathVariable Integer id) {
        if (!service.existsById(id)) {
            throw WAREHOUSE_NOT_FOUND;
        }

        service.updateWarehouse(id, warehouse);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteWarehouse(id);
    }

    @GetMapping("/{id}/products")
    public List<WarehouseProductInfo> productsInAWarehouse(@PathVariable Integer id) {
        if (service.existsById(id)) {
            throw WAREHOUSE_NOT_FOUND;
        }
        return service.getProductsByWarehouseId(id);
    }

    @GetMapping("/products")
    public List<WarehouseProductInfo> productsInAllWarehouses() {
        return service.getProductsInWarehouses();
    }

    @PostMapping("/{id}/products")
    public void addProductToWarehouse(@PathVariable Integer id, @RequestBody WarehouseProductInfo productInfo) {
        service.addProductToWarehouse(id, productInfo.getProductId(), productInfo.getQuantity());
    }
}
