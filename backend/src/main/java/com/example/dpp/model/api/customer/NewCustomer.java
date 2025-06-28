package com.example.dpp.model.api.customer;

import com.example.dpp.model.api.AddressInfo;

public class NewCustomer {
    private String customerFirstName;
    private String customerLastName;
    private AddressInfo customerAddress;
    private String customerEmail;
    private String customerPhone;

    public NewCustomer() {
    }

    public NewCustomer(String customerFirstName, String customerLastName, AddressInfo customerAddress, String customerEmail, String customerPhone) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerAddress = customerAddress;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
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
