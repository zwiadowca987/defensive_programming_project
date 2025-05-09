package com.example.dpp.controller;

import com.example.dpp.model.api.products.PurchaseCreation;
import com.example.dpp.model.api.products.PurchaseInfo;
import com.example.dpp.services.IPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    public static final ResponseStatusException ORDER_NOT_FOUND = new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
    private final IPurchaseService service;

    @Autowired
    public OrderController(IPurchaseService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<PurchaseInfo> findAll() {
        return service.getPurchases();
    }

    @GetMapping("/{id}")
    public PurchaseInfo findById(@PathVariable Integer id) {
        var purchase = service.getPurchase(id);
        if (purchase == null) {
            throw OrderController.ORDER_NOT_FOUND;
        }
        return purchase;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@RequestBody PurchaseCreation purchase) {
        service.createPurchase(purchase);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody PurchaseCreation purchase, @PathVariable Integer id) {
        if (!service.existsById(id)) {
            throw ORDER_NOT_FOUND;
        }

        service.updatePurchase(id, purchase);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deletePurchase(id);
    }
}
