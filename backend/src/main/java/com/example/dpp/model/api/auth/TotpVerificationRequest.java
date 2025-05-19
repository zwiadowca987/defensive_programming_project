package com.example.dpp.model.api.auth;

public class TotpVerificationRequest {
    private String username;
    private String totpCode;

    public TotpVerificationRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTotpCode() {
        return totpCode;
    }

    public void setTotpCode(String totpCode) {
        this.totpCode = totpCode;
    }
}

