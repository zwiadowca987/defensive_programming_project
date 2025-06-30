package com.example.dpp.model.db.products;


import com.example.dpp.model.PurchaseStatus;
import com.example.dpp.model.api.products.PurchaseInfo;
import com.example.dpp.model.db.customer.Customer;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private LocalDateTime date;

    @Column(name = "status")
    private PurchaseStatus status;

    @OneToMany(mappedBy = "purchase")
    private Set<PurchaseDetails> purchaseInfos;

    public Purchase() {
        purchaseInfos = new HashSet<>();
    }

    public Purchase(Integer id, Customer customer, LocalDateTime date, PurchaseStatus status) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
                .map(x -> (x.getProduct() == null ? BigDecimal.ZERO : x.getProduct().getPrice().multiply(BigDecimal.valueOf(x.getQuantity()))))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        purchaseInfo.setStatus(getStatus());

        var list = purchaseInfos.stream().map(PurchaseDetails::convertToPurchaseProductInfo).toList();

        purchaseInfo.setProducts(list);
        return purchaseInfo;
    }
}
