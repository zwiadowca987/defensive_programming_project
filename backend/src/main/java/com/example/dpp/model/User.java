package com.example.dpp.model;

public record User(
        Integer id,
        String userName,
        String firstName,
        String lastName,
        String password,
        Role role
) {
}
