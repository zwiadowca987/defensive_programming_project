package com.example.dpp.controller;

import com.example.dpp.model.products.Order;
import com.example.dpp.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    public static final ResponseStatusException ORDER_NOT_FOUND = new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
    private final OrderRepository repository;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<Order> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> ORDER_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@RequestBody Order order) {
        repository.save(order);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Order order, @PathVariable Integer id) {
        if (!repository.existsById(id)) {
            throw ORDER_NOT_FOUND;
        }

        repository.deleteById(id);
        repository.save(order);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
