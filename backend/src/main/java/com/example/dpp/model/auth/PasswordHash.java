package com.example.dpp.model.auth;

import jakarta.persistence.*;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Entity
@Table(name = "password")
public class PasswordHash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "iterations", nullable = false)
    private int iterations;

    @Column(name = "hash_length", nullable = false)
    private int hashLength;

    @Column(name = "memory_cost", nullable = false)
    private int memoryCost;

    @Column(name = "parallelism", nullable = false)
    private int parallelismFactor;

    @Column(name = "salt_length", nullable = false)
    private int saltLength;

    @Column(name = "hash", nullable = false, length=511)
    private String hash;

    @OneToOne(mappedBy = "password")
    private User user;

    public PasswordHash() {}

    public PasswordHash(String password) {
        iterations = 10;
        hashLength = 128;
        memoryCost = 100000;
        parallelismFactor = 2;
        saltLength = 64;
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

    public int getParallelismFactor() {
        return parallelismFactor;
    }

    public void setParallelismFactor(int parallelismFactor) {
        this.parallelismFactor = parallelismFactor;
    }

    public int getMemoryCost() {
        return memoryCost;
    }

    public void setMemoryCost(int memoryCost) {
        this.memoryCost = memoryCost;
    }

    public int getHashLength() {
        return hashLength;
    }

    public void setHashLength(int hashLength) {
        this.hashLength = hashLength;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSaltLength() {
        return saltLength;
    }

    public void setSaltLength(int timeCost) {
        this.saltLength = timeCost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
