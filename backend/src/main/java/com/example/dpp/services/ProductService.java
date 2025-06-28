package com.example.dpp.services;

import com.example.dpp.model.api.products.ProductCreation;
import com.example.dpp.model.api.products.ProductInfo;
import com.example.dpp.model.api.warehouses.WarehouseProductInfo;
import com.example.dpp.model.db.products.Product;
import com.example.dpp.repository.ProductRepository;
import com.example.dpp.repository.WarehouseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private final ProductRepository repository;
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, WarehouseRepository warehouseRepository) {
        this.repository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public List<ProductInfo> getProducts() {
        return repository.findAll().stream().map(Product::convertToProductInfo).collect(Collectors.toList());
    }

    @Override
    public ProductInfo getProduct(int id) {
        return repository.findById(id).map(Product::convertToProductInfo).orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
    }

    @Override
    public ProductInfo createProduct(ProductCreation product) {
        var newProduct = new Product();
        newProduct.setProductName(product.getProductName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setProducer(product.getProducer());
        repository.save(newProduct);
        return newProduct.convertToProductInfo();
    }

    @Override
    public ProductInfo updateProduct(ProductInfo product) {
        var oldProduct = repository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException("Product with id " + product.getId() + " does not exist"));

        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setProducer(product.getProducer());
        oldProduct.setProductName(product.getProductName());

        repository.save(oldProduct);
        return oldProduct.convertToProductInfo();
    }

    @Override
    public ProductInfo updateProduct(int id, ProductCreation product) {
        var oldProduct = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Product with id " + id + " does not exist"));

        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setProducer(product.getProducer());
        oldProduct.setProductName(product.getProductName());

        repository.save(oldProduct);
        return oldProduct.convertToProductInfo();
    }

    @Override
    public boolean deleteProduct(int id) {
        if (!repository.existsById(id))
            return false;
        repository.deleteById(id);
        return true;
    }

    @Override
    public List<WarehouseProductInfo> getWarehouseProductAvailability(int id) {
        var product = repository.findById(id).map(Product::convertToProductInfo).orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
        return warehouseRepository.findAll().stream().flatMap(
                warehouse
                        -> warehouse.getProductsList().stream()
                        .filter(productsList -> productsList.getProduct().getId() == id)
                        .map(productsList -> {
                            var info = new WarehouseProductInfo();
                            info.setProductName(productsList.getProduct().getProductName());
                            info.setProductId(productsList.getProduct().getId());
                            info.setWarehouseName(warehouse.getName());
                            info.setWarehouseId(warehouse.getId());
                            info.setQuantity(productsList.getQuantity());
                            return info;
                        })).collect(Collectors.toList());

    }

    @Override
    public List<ProductInfo> searchProduct(String keyword) {

        return repository.findByProductNameContainingIgnoreCase(keyword)
                .stream().map(Product::convertToProductInfo).collect(Collectors.toList());
    }
}
