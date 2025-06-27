package com.example.dpp.services;

import com.example.dpp.model.api.customer.CustomerInfo;
import com.example.dpp.model.api.customer.NewCustomer;

import java.util.List;

public interface ICustomerService {
    List<CustomerInfo> getAllCustomers();

    CustomerInfo getCustomerById(int id);

    CustomerInfo createCustomer(NewCustomer newCustomer);

    CustomerInfo updateCustomer(int id, NewCustomer editCustomer);

    void deleteCustomer(int id);


}
