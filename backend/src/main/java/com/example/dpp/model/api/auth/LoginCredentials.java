package com.example.dpp.model.api.auth;

public class LoginCredentials {
    private String email;
    private String password;
    private String totpCode;

    public LoginCredentials(String mail, String password) {
        this.email = mail;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTotpCode() {
        return totpCode;
    }

    public void setTotpCode(String totpCode) {
        this.totpCode = totpCode;
    }
}
