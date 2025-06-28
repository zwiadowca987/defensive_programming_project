package com.example.dpp.repository;

import com.example.dpp.model.db.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProductNameContainingIgnoreCase(String name);

    List<Product> findByDescriptionContainingIgnoreCase(String description);

    List<Product> findByProducerContainingIgnoreCase(String name);
}
