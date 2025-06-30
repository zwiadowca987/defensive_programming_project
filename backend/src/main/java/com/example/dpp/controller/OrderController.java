package com.example.dpp.controller;

import com.example.dpp.model.api.order.OrderProductList;
import com.example.dpp.model.api.products.PurchaseCreation;
import com.example.dpp.model.api.products.PurchaseInfo;
import com.example.dpp.services.PurchaseService;
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
    private final PurchaseService service;

    @Autowired
    public OrderController(PurchaseService service) {
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
        service.updatePurchase(id, purchase);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deletePurchase(id);
    }

    @PostMapping("/{id}/products")
    public PurchaseInfo addProduct(@RequestBody OrderProductList orderProductList, @PathVariable Integer id) {
        return service.addProduct(id, orderProductList.getProductId(), orderProductList.getQuantity());
    }

    @PutMapping("/{id}/products")
    public PurchaseInfo updateProduct(@RequestBody OrderProductList orderProductList, @PathVariable Integer id) {
        return service.updateProductQuantity(id, orderProductList.getProductId(), orderProductList.getQuantity());
    }

    @DeleteMapping("/{id}/products")
    public void deleteProduct(@RequestBody OrderProductList orderProductList, @PathVariable Integer id) {
        service.removeProduct(id, orderProductList.getProductId());
    }


}
