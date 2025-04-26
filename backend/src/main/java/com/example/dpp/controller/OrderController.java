package com.example.dpp.controller;

import com.example.dpp.model.products.Purchase;
import com.example.dpp.repository.PurchaseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    public static final ResponseStatusException ORDER_NOT_FOUND = new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
    private final PurchaseRepository repository;

    public OrderController(PurchaseRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<Purchase> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Purchase findById(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> ORDER_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@RequestBody Purchase purchase) {
        repository.save(purchase);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Purchase purchase, @PathVariable Integer id) {
        if (!repository.existsById(id)) {
            throw ORDER_NOT_FOUND;
        }

        repository.deleteById(id);
        repository.save(purchase);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
