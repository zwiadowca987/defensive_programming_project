package com.example.dpp.controller;

import com.example.dpp.model.api.customer.CustomerInfo;
import com.example.dpp.model.api.customer.NewCustomer;
import com.example.dpp.model.api.products.PurchaseInfo;
import com.example.dpp.services.CustomerService;
import com.example.dpp.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private CustomerService customerService;
    private PurchaseService purchaseService;

    @Autowired
    public CustomerController(CustomerService customerService, PurchaseService purchaseService) {
        this.customerService = customerService;
        this.purchaseService = purchaseService;
    }

    @GetMapping("")
    public List<CustomerInfo> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerInfo getCustomer(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/add")
    public CustomerInfo addCustomer(@RequestBody NewCustomer customer) {
        return customerService.createCustomer(customer);
    }

    @PostMapping("{id}/delete")
    public void deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
    }

    @PostMapping("{id}/update")
    public CustomerInfo updateCustomer(@PathVariable int id, @RequestBody NewCustomer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @GetMapping("{id}/orders")
    public List<PurchaseInfo> getCustomerOrders(@PathVariable int id) {
        return purchaseService.getPurchasesByCustomerId(id);
    }
}
