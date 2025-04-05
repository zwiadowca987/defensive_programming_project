package com.example.dpp.model;

import jakarta.persistence.*;
import org.hibernate.Length;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Entity
@Table(name = "password")
public class PasswordHash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "iterations", nullable = false)
    private Integer iterations;

    @Column(name = "hash_length", nullable = false)
    private Integer hashLength;

    @Column(name = "memory_cost", nullable = false)
    private Integer memoryCost;

    @Column(name = "parallelism", nullable = false)
    private Integer parallelismFactor;

    @Column(name = "salt_length", nullable = false)
    private Integer saltLength;

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

    public Integer getParallelismFactor() {
        return parallelismFactor;
    }

    public void setParallelismFactor(Integer parallelismFactor) {
        this.parallelismFactor = parallelismFactor;
    }

    public Integer getMemoryCost() {
        return memoryCost;
    }

    public void setMemoryCost(Integer memoryCost) {
        this.memoryCost = memoryCost;
    }

    public Integer getHashLength() {
        return hashLength;
    }

    public void setHashLength(Integer hashLength) {
        this.hashLength = hashLength;
    }

    public Integer getIterations() {
        return iterations;
    }

    public void setIterations(Integer iterations) {
        this.iterations = iterations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSaltLength() {
        return saltLength;
    }

    public void setSaltLength(Integer timeCost) {
        this.saltLength = timeCost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
