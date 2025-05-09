package com.example.dpp.services;

import com.example.dpp.model.api.products.AddProduct;
import com.example.dpp.model.api.products.ProductInfo;
import com.example.dpp.model.db.products.Product;
import com.example.dpp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService  implements IProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    @Override
    public List<ProductInfo> getProducts() {
        return repository.findAll().stream().map(Product::convertToProductInfo).collect(Collectors.toList());
    }

    @Override
    public ProductInfo getProduct(int id) {
        return repository.findById(id).map(Product::convertToProductInfo).orElse(null);
    }

    @Override
    public ProductInfo createProduct(AddProduct product) {
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
                .orElseThrow( () -> new IllegalStateException("Product with id " + product.getId() + " does not exist"));

        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setProducer(product.getProducer());
        oldProduct.setProductName(product.getProductName());

        repository.save(oldProduct);
        return oldProduct.convertToProductInfo();
    }

    @Override
    public ProductInfo updateProduct(int id, AddProduct product) {
        var oldProduct = repository.findById(id)
                .orElseThrow( () -> new IllegalStateException("Product with id " + id + " does not exist"));

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
}
