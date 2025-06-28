package com.example.dpp.services;

import com.example.dpp.model.api.customer.CustomerInfo;
import com.example.dpp.model.api.customer.NewCustomer;
import com.example.dpp.model.db.Address;
import com.example.dpp.model.db.customer.Customer;
import com.example.dpp.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CustomerInfo> getAllCustomers() {
        return repository.findAll()
                .stream()
                .map(CustomerInfo::new)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerInfo getCustomerById(int id) {
        return new CustomerInfo(
                repository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Customer not found")));
    }

    @Override
    public CustomerInfo createCustomer(NewCustomer newCustomer) {
        var customer = new Customer();
        customer.setCustomerFirstName(newCustomer.getCustomerFirstName());
        customer.setCustomerLastName(newCustomer.getCustomerLastName());
        customer.setCustomerEmail(newCustomer.getCustomerEmail());
        customer.setCustomerPhone(newCustomer.getCustomerPhone());

        var address = new Address();
        address.setCity(newCustomer.getCustomerAddress().getCity());
        address.setCountry(newCustomer.getCustomerAddress().getCountry());
        address.setPostalCode(newCustomer.getCustomerAddress().getPostalCode());
        address.setStreet(newCustomer.getCustomerAddress().getStreet());
        address.setNumber(String.valueOf(newCustomer.getCustomerAddress().getNumber()));

        customer.setCustomerAddress(address);

        repository.save(customer);

        return new CustomerInfo(customer);
    }

    @Override
    public CustomerInfo updateCustomer(int id, NewCustomer editCustomer) {
        var customer = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customer.setCustomerFirstName(editCustomer.getCustomerFirstName());
        customer.setCustomerLastName(editCustomer.getCustomerLastName());
        customer.setCustomerEmail(editCustomer.getCustomerEmail());
        customer.setCustomerPhone(editCustomer.getCustomerPhone());

        customer.getCustomerAddress().setCity(editCustomer.getCustomerAddress().getCity());
        customer.getCustomerAddress().setCountry(editCustomer.getCustomerAddress().getCountry());
        customer.getCustomerAddress().setPostalCode(editCustomer.getCustomerAddress().getPostalCode());
        customer.getCustomerAddress().setStreet(editCustomer.getCustomerAddress().getStreet());
        customer.getCustomerAddress().setNumber(String.valueOf(editCustomer.getCustomerAddress().getNumber()));

        repository.save(customer);

        return new CustomerInfo(customer);
    }

    @Override
    public void deleteCustomer(int id) {
        repository.deleteById(id);
    }

    @Override
    public CustomerInfo getCustomerByEmail(String email) {
        return new CustomerInfo(repository.findByCustomerEmail(email).orElseThrow(() -> new EntityNotFoundException("Costumer with email " + email + "not found")));
    }
}
