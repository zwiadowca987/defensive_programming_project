package com.example.dpp.model.db.products;

import com.example.dpp.model.api.products.PurchaseInfo;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "client_id")
    private int clientId;

    @Column(name="date")
    private Date date;

    @Column(name = "status")
    private Status status;

    @OneToMany
    private Set<PurchaseDetails> purchaseInfos;

    public Purchase() {
    }

    public Purchase(Integer id, Integer clientId, Date date, double price, Status status) {
        this.id = id;
        this.clientId = clientId;
        this.date = date;
        this.status = status;
        purchaseInfos = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<PurchaseDetails> getPurchaseInfos() {
        return purchaseInfos;
    }

    public void setPurchaseInfos(Set<PurchaseDetails> purchaseInfos) {
        this.purchaseInfos = purchaseInfos;
    }

    public void addPurchaseDetails(PurchaseDetails purchaseDetails) {
        purchaseInfos.add(purchaseDetails);
    }

    public PurchaseInfo convertToPurchaseInfo(){
        PurchaseInfo purchaseInfo = new PurchaseInfo();

        purchaseInfo.setClientId(getClientId());
        purchaseInfo.setDate(getDate());
        purchaseInfo.setPrice(purchaseInfos.stream()
                .map( x -> x.getQuantity() * x.getProduct().getPrice())
                .reduce(0.0, Double::sum));
        purchaseInfo.setStatus(getStatus());

        var list = purchaseInfos.stream().map(PurchaseDetails::convertToPurchaseProductInfo).toList();

        purchaseInfo.setProducts(list);
        return purchaseInfo;
    }
}
