package com.example.dpp.model.api;

import com.example.dpp.model.db.Address;

public class AddressInfo {
    private String country;
    private String city;
    private String postalCode;
    private String street;
    private String number;

    public AddressInfo() {
    }

    public AddressInfo(String country, String city, String postalCode, String street, String number) {
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.number = number;
    }

    public AddressInfo(Address address) {
        this.country = address.getCountry();
        this.city = address.getCity();
        this.postalCode = address.getPostalCode();
        this.street = address.getStreet();
        this.number = address.getNumber();
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
