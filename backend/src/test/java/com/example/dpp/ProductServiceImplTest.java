package com.example.dpp;

import com.example.dpp.model.api.products.ProductCreation;
import com.example.dpp.model.api.products.ProductInfo;
import com.example.dpp.model.api.warehouses.WarehouseProductInfo;
import com.example.dpp.model.db.products.Product;
import com.example.dpp.model.db.warehouses.ProductsList;
import com.example.dpp.model.db.warehouses.Warehouse;
import com.example.dpp.repository.ProductRepository;
import com.example.dpp.repository.WarehouseRepository;
import com.example.dpp.services.ProductServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, warehouseRepository);
    }

    @Test
    void getProduct_existingId_returnsProductInfo() {
        // given
        int productId = 1;
        Product product = new Product();

        product.setId(productId);
        product.setProductName("Test Product");
        product.setDescription("Test Desc");
        product.setPrice(new BigDecimal("99.99"));
        product.setProducer("Test Inc");

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // when
        ProductInfo result = productService.getProduct(productId);

        // then
        assertNotNull(result);
        assertEquals("Test Product", result.getProductName());
        assertEquals("Test Desc", result.getDescription());
    }

    @Test
    void createProduct_savesAndReturnsProductInfo() {
        // given
        ProductCreation creation = new ProductCreation();
        creation.setProductName("New Product");
        creation.setDescription("New Desc");
        creation.setPrice(new BigDecimal("49.99"));
        creation.setProducer("New Co");

        // simulate that repository returns the same entity we save
        Mockito.when(productRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        ProductInfo result = productService.createProduct(creation);

        // then
        assertNotNull(result);
        assertEquals("New Product", result.getProductName());
        assertEquals(new BigDecimal("49.99"), result.getPrice());

    }

    @Test
    void updateProduct_existingProduct_updatesAndReturnsInfo() {
        // given
        int productId = 1;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setProductName("Old");
        existingProduct.setDescription("Old");
        existingProduct.setPrice(new BigDecimal("10.00"));
        existingProduct.setProducer("Old");

        ProductInfo input = new ProductInfo();
        input.setId(productId);
        input.setProductName("New");
        input.setDescription("New Desc");
        input.setPrice(new BigDecimal("99.99"));
        input.setProducer("New Inc");

        Mockito.when(productRepository.findById(productId))
                .thenReturn(Optional.of(existingProduct));

        // when
        productService.updateProduct(input);

        // then
        // Sprawdzamy czy wartości zostały zaktualizowane
        assertEquals("New", existingProduct.getProductName());
        assertEquals("New Desc", existingProduct.getDescription());
        assertEquals(new BigDecimal("99.99"), existingProduct.getPrice());
        assertEquals("New Inc", existingProduct.getProducer());

        // Sprawdzamy czy zapisano obiekt
        Mockito.verify(productRepository).save(existingProduct);
    }

    @Test
    void updateProduct_nonExistingProduct_throwsException() {
        // given
        int productId = 999;
        ProductInfo updatedInfo = new ProductInfo();
        updatedInfo.setId(productId);
        updatedInfo.setProductName("Doesn't Matter");

        Mockito.when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        // when + then
        assertThrows(IllegalStateException.class, () -> {
            productService.updateProduct(updatedInfo);
        });
    }

    @Test
    void getProducts_shouldReturnAllProductsAsInfoList() {
        // given
        Product p1 = new Product();
        p1.setId(1);
        p1.setProductName("Prod A");
        Product p2 = new Product();
        p2.setId(2);
        p2.setProductName("Prod B");

        Mockito.when(productRepository.findAll())
                .thenReturn(List.of(p1, p2));

        // when
        List<ProductInfo> result = productService.getProducts();

        // then
        assertEquals(2, result.size());
        assertEquals("Prod A", result.get(0).getProductName());
        assertEquals("Prod B", result.get(1).getProductName());
    }


    @Test
    void getProducts_shouldReturnEmptyListWhenNoProducts() {
        // given
        Mockito.when(productRepository.findAll())
                .thenReturn(Collections.emptyList());

        // when
        List<ProductInfo> result = productService.getProducts();

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    @Test
    void getProduct_shouldReturnProductInfoIfExists() {
        // given
        int id = 10;
        Product product = new Product();
        product.setId(id);
        product.setProductName("Test");

        Mockito.when(productRepository.findById(id))
                .thenReturn(Optional.of(product));

        // when
        ProductInfo result = productService.getProduct(id);

        // then
        assertNotNull(result);
        assertEquals("Test", result.getProductName());
        assertEquals(id, result.getId());
    }


    @Test
    void getProduct_shouldThrowIfProductNotFound() {
        // given
        int id = 999;
        Mockito.when(productRepository.findById(id))
                .thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> productService.getProduct(id));
    }

    @Test
    void updateProduct_shouldUpdateExistingProductAndReturnInfo() {
        // given
        int id = 1;
        Product existing = new Product();
        existing.setId(id);
        existing.setProductName("Old");
        existing.setDescription("Old");
        existing.setPrice(new BigDecimal("19.99"));
        existing.setProducer("Old");

        ProductCreation input = new ProductCreation();
        input.setProductName("New Name");
        input.setDescription("New Desc");
        input.setPrice(new BigDecimal("49.99"));
        input.setProducer("New Inc");

        Mockito.when(productRepository.findById(id))
                .thenReturn(Optional.of(existing));
        Mockito.when(productRepository.save(Mockito.any()))
                .thenAnswer(inv -> inv.getArgument(0));

        // when
        ProductInfo result = productService.updateProduct(id, input);

        // then
        assertEquals("New Name", result.getProductName());
        assertEquals("New Desc", result.getDescription());
        assertEquals(new BigDecimal("49.99"), result.getPrice());
        assertEquals("New Inc", result.getProducer());

        Mockito.verify(productRepository).save(existing);
    }

    @Test
    void updateProduct_shouldThrowIfProductDoesNotExist() {
        // given
        int id = 999;
        ProductCreation input = new ProductCreation();
        input.setProductName("Ignored");

        Mockito.when(productRepository.findById(id))
                .thenReturn(Optional.empty());

        // then
        assertThrows(IllegalStateException.class, () -> productService.updateProduct(id, input));
    }

    @Test
    void deleteProduct_shouldDeleteAndReturnTrue() {
        // given
        int id = 5;
        Mockito.when(productRepository.existsById(id)).thenReturn(true);

        // when
        boolean result = productService.deleteProduct(id);

        // then
        assertTrue(result);
        Mockito.verify(productRepository).deleteById(id);
    }

    @Test
    void deleteProduct_shouldReturnFalseIfProductNotFound() {
        // given
        int id = 6;
        Mockito.when(productRepository.existsById(id)).thenReturn(false);

        // when
        boolean result = productService.deleteProduct(id);

        // then
        assertFalse(result);
        Mockito.verify(productRepository, Mockito.never()).deleteById(Mockito.any());
    }

    @Test
    void getWarehouseProductAvailability_shouldReturnProductFromWarehouses() {
        // given
        int productId = 1;
        Product product = new Product();
        product.setId(productId);
        product.setProductName("Mocked Product");

        ProductsList pl = new ProductsList(product, 10);
        ProductsList pl_s = new ProductsList(product, 1);


        Warehouse warehouse = new Warehouse();
        warehouse.setId(100);
        warehouse.setName("Main Warehouse");
        warehouse.setProductsList(List.of(pl));

        Warehouse warehouse_2 = new Warehouse();
        warehouse_2.setId(100);
        warehouse_2.setName("Small Warehouse");
        warehouse_2.setProductsList(List.of(pl_s));

        Mockito.when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
        Mockito.when(warehouseRepository.findAll())
                .thenReturn(List.of(warehouse, warehouse_2));

        // when
        List<WarehouseProductInfo> result = productService.getWarehouseProductAvailability(productId);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        WarehouseProductInfo info = result.get(0);
        assertEquals("Mocked Product", info.getProductName());
        assertEquals(10, info.getQuantity());
        assertEquals("Main Warehouse", info.getWarehouseName());

        WarehouseProductInfo info2 = result.get(1);
        assertEquals("Mocked Product", info2.getProductName());
        assertEquals(1, info2.getQuantity());
        assertEquals("Small Warehouse", info2.getWarehouseName());
    }
}

