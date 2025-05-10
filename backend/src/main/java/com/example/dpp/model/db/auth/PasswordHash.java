package com.example.dpp.model.db.auth;

import jakarta.persistence.*;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Entity
@Table(name = "password")
public class PasswordHash {

    private static final Integer ITERATIONS = 3;
    private static final Integer SALT_LENGTH = 64;
    private static final Integer PARALLELISM_FACTOR = 1;
    private static final Integer HASH_LENGTH = 125;
    private static final Integer MEMORY_COST = 12288;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "hash", nullable = false, length = 511)
    private String hash;

    @OneToOne(mappedBy = "password")
    private User user;

    public PasswordHash() {
    }

    public PasswordHash(String password) {

        var arg2SpringSecurity = new Argon2PasswordEncoder(
                getSaltLength(),
                getHashLength(),
                getParallelismFactor(),
                getMemoryCost(),
                getIterations());
        hash = arg2SpringSecurity.encode(password);
    }

    public boolean checkPassword(String password) {
        var arg2SpringSecurity = new Argon2PasswordEncoder(
                getSaltLength(),
                getHashLength(),
                getParallelismFactor(),
                getMemoryCost(),
                getIterations());
        return arg2SpringSecurity.matches(password, getHash());
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getParallelismFactor() {
        return PARALLELISM_FACTOR;
    }

    public Integer getMemoryCost() {
        return MEMORY_COST;
    }

    public Integer getHashLength() {
        return HASH_LENGTH;
    }

    public Integer getIterations() {
        return ITERATIONS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSaltLength() {
        return SALT_LENGTH;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
