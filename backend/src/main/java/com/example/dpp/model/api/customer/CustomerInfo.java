package com.example.dpp.model.api.customer;

import com.example.dpp.model.api.AddressInfo;
import com.example.dpp.model.db.customer.Customer;

public class CustomerInfo {
    private int id;
    private String customerFirstName;
    private String customerLastName;
    private AddressInfo customerAddress;
    private String customerEmail;
    private String customerPhone;

    public CustomerInfo() {
    }

    public CustomerInfo(int id, String customerFirstName, String customerLastName, AddressInfo customerAddress, String customerEmail, String customerPhone) {
        this.id = id;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerAddress = customerAddress;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
    }

    public CustomerInfo(Customer customer) {
        this.id = customer.getId();
        this.customerFirstName = customer.getCustomerFirstName();
        this.customerLastName = customer.getCustomerLastName();
        this.customerEmail = customer.getCustomerEmail();
        this.customerPhone = customer.getCustomerPhone();
        this.customerAddress = new AddressInfo(customer.getCustomerAddress());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public AddressInfo getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(AddressInfo customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}
