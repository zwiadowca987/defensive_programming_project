package com.example.dpp.repository;

import com.example.dpp.model.db.products.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
}
