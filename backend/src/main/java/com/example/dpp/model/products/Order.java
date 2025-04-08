package com.example.dpp.model.products;

import jakarta.persistence.*;

import java.util.Date;

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "client_id")
    private Integer clientId;

    @Column(name="date")
    private Date date;

    @Column(name="price")
    private double price;

    @Column(name = "status")
    private Status status;

    public Order() {
    }

    public Order(Integer id, Integer clientId, Date date, double price, Status status) {
        this.id = id;
        this.clientId = clientId;
        this.date = date;
        this.price = price;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
