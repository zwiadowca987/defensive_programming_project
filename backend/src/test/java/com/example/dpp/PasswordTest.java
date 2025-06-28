package com.example.dpp;

import com.example.dpp.model.db.auth.PasswordHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PasswordTest {

    private PasswordHash password;

    @BeforeEach
    void setUp() {
        password = new PasswordHash("SuperPassword");
    }

    @Test
    @DisplayName("Correct password test")
    void TestHashingPassword() {
        assertTrue(password.checkPassword("SuperPassword"));
    }

    @Test
    @DisplayName("Incorrect password test")
    void TestHashingPassword2() {
        assertFalse(password.checkPassword("SuperPassword1"));
    }
}
