package com.example.dpp.controller;

import com.example.dpp.model.api.products.AddProduct;
import com.example.dpp.model.api.products.ProductInfo;
import com.example.dpp.model.db.products.Product;
import com.example.dpp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    public static final ResponseStatusException PRODUCT_NOT_FOUND = new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    private final IProductService service;

    @Autowired
    public ProductController(IProductService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<ProductInfo> findAll() {
        return service.getProducts();
    }

    @GetMapping("/{id}")
    public ProductInfo findById(@PathVariable Integer id) {
        var product = service.getProduct(id);
        if (product == null) {
            throw PRODUCT_NOT_FOUND;
        }
        return product;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@RequestBody AddProduct product) {
        service.createProduct(product);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody AddProduct product, @PathVariable Integer id) {

        if (service.getProduct(id) == null){
            throw PRODUCT_NOT_FOUND;
        }

        service.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteProduct(id);
    }
}
