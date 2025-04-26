package com.example.dpp.controller;

import com.example.dpp.model.products.Product;
import com.example.dpp.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    public static final ResponseStatusException PRODUCT_NOT_FOUND = new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<Product> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> PRODUCT_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@RequestBody Product product) {
        repository.save(product);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Product product, @PathVariable Integer id) {
        if (!repository.existsById(id)){
            throw PRODUCT_NOT_FOUND;
        }

        repository.deleteById(id);
        repository.save(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
