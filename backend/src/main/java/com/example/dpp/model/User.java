package com.example.dpp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public record User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Integer id,

        @Column(name = "user_name", nullable = false, unique = true)
        String userName,
        String firstName,
        String lastName,
        String password,

        @Enumerated(EnumType.STRING)
        Role role
) {
}
