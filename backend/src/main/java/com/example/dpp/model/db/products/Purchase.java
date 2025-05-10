package com.example.dpp.model.db.products;


import com.example.dpp.model.PurchaseStatus;
import com.example.dpp.model.api.products.PurchaseInfo;
import com.example.dpp.model.db.Customer;
import jakarta.persistence.*;

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

    @ManyToOne
    private Customer customer;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private PurchaseStatus status;

    @OneToMany(mappedBy = "purchase")
    private Set<PurchaseDetails> purchaseInfos;

    public Purchase() {
    }

    public Purchase(Integer id, Customer customer, Date date, PurchaseStatus status) {
        this.id = id;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PurchaseStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseStatus status) {
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

    public PurchaseInfo convertToPurchaseInfo() {
        PurchaseInfo purchaseInfo = new PurchaseInfo();

        purchaseInfo.setClientId(customer.getId());
        purchaseInfo.setDate(getDate());
        purchaseInfo.setPrice(purchaseInfos.stream()
                .map(x -> x.getQuantity() * x.getProduct().getPrice())
                .reduce(0.0, Double::sum));
        purchaseInfo.setStatus(getStatus());

        var list = purchaseInfos.stream().map(PurchaseDetails::convertToPurchaseProductInfo).toList();

        purchaseInfo.setProducts(list);
        return purchaseInfo;
    }
}
