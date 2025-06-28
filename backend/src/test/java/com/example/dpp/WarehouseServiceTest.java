package com.example.dpp;

import com.example.dpp.model.api.warehouses.WarehouseCreation;
import com.example.dpp.model.api.warehouses.WarehouseInfo;
import com.example.dpp.model.api.warehouses.WarehouseProductInfo;
import com.example.dpp.model.db.products.Product;
import com.example.dpp.model.db.warehouses.ProductsList;
import com.example.dpp.model.db.warehouses.Warehouse;
import com.example.dpp.repository.ProductRepository;
import com.example.dpp.repository.WarehouseRepository;
import com.example.dpp.services.WarehouseService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private WarehouseService warehouseService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getWarehouse_success() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1);
        warehouse.setName("Main Warehouse");

        when(warehouseRepository.findById(1)).thenReturn(Optional.of(warehouse));

        var result = warehouseService.getWarehouse(1);

        assertEquals(1, result.getId());
        assertEquals("Main Warehouse", result.getName());
    }

    @Test
    void getWarehouse_notFound() {
        when(warehouseRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> warehouseService.getWarehouse(1));
    }

    @Test
    void addWarehouse_success() {
        WarehouseCreation creation = new WarehouseCreation();
        creation.setWarehouseName("New Warehouse");

        assertTrue(warehouseService.addWarehouse(creation));
        verify(warehouseRepository, times(1)).save(any(Warehouse.class));
    }

    @Test
    void updateWarehouse_success() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1);
        warehouse.setName("Old");

        when(warehouseRepository.findById(1)).thenReturn(Optional.of(warehouse));

        WarehouseCreation updated = new WarehouseCreation();
        updated.setWarehouseName("Updated Name");

        assertTrue(warehouseService.updateWarehouse(1, updated));
        verify(warehouseRepository).save(warehouse);
        assertEquals("Updated Name", warehouse.getName());
    }

    @Test
    void updateWarehouse_notFound() {
        when(warehouseRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> warehouseService.updateWarehouse(1, new WarehouseCreation()));
    }

    @Test
    void deleteWarehouse_success() {
        assertTrue(warehouseService.deleteWarehouse(1));
        verify(warehouseRepository).deleteById(1);
    }

    @Test
    void addProductToWarehouse_success() {
        Warehouse warehouse = mock(Warehouse.class);
        Product product = new Product();

        when(warehouseRepository.findById(1)).thenReturn(Optional.of(warehouse));
        when(productRepository.findById(2)).thenReturn(Optional.of(product));

        boolean result = warehouseService.addProductToWarehouse(1, 2, 5);

        assertTrue(result);
        verify(warehouse).AddProductToWarehouse(product, 5);
    }

    @Test
    void addProductToWarehouse_invalidAmount() {
        assertThrows(EntityNotFoundException.class, () -> warehouseService.addProductToWarehouse(1, 2, 0));
    }

    @Test
    void addProductToWarehouse_warehouseNotFound() {
        when(warehouseRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> warehouseService.addProductToWarehouse(1, 2, 5));
    }

    @Test
    void addProductToWarehouse_productNotFound() {
        when(warehouseRepository.findById(1)).thenReturn(Optional.of(new Warehouse()));
        when(productRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> warehouseService.addProductToWarehouse(1, 2, 5));
    }

    @Test
    void getProductsInWarehouses_noWarehouses() {
        when(warehouseRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(IllegalStateException.class, () -> warehouseService.getProductsInWarehouses());
    }

    @Test
    void getAllWarehouses_success() {
        Warehouse w1 = new Warehouse();
        w1.setId(1);
        w1.setName("A");

        Warehouse w2 = new Warehouse();
        w2.setId(2);
        w2.setName("B");

        when(warehouseRepository.findAll()).thenReturn(List.of(w1, w2));

        List<WarehouseInfo> result = warehouseService.getAllWarehouses();
        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getName());
    }

    @Test
    void getProductsByWarehouseId_success() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1);
        warehouse.setName("Warehouse");

        Product product = new Product();
        product.setId(2);
        product.setProductName("Item");

        ProductsList wp = new ProductsList();
        wp.setProduct(product);
        wp.setQuantity(10);

        warehouse.setProductsList(List.of(wp));

        when(warehouseRepository.findById(1)).thenReturn(Optional.of(warehouse));

        List<WarehouseProductInfo> products = warehouseService.getProductsByWarehouseId(1);

        assertEquals(1, products.size());
        assertEquals("Item", products.get(0).getProductName());
    }

    @Test
    void getProductsByWarehouseId_notFound() {
        when(warehouseRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> warehouseService.getProductsByWarehouseId(1));
    }

    @Test
    void existsById_true() {
        when(warehouseRepository.existsById(1)).thenReturn(true);
        assertTrue(warehouseService.existsById(1));
    }

    @Test
    void existsById_false() {
        when(warehouseRepository.existsById(1)).thenReturn(false);
        assertFalse(warehouseService.existsById(1));
    }
}