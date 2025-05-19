package com.example.dpp.model.api.auth;

public class TotpSetupResponse {
    private boolean success;
    private String message;
    private String qrCodeImage;
    private String mfaSecret;

    public TotpSetupResponse() {
    }

    public TotpSetupResponse(boolean success, String mfaSecret, String qrCodeImage, String message) {
        this.success = success;
        this.mfaSecret = mfaSecret;
        this.qrCodeImage = qrCodeImage;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQrCodeImage() {
        return qrCodeImage;
    }

    public void setQrCodeImage(String qrCodeImage) {
        this.qrCodeImage = qrCodeImage;
    }

    public String getMfaSecret() {
        return mfaSecret;
    }

    public void setMfaSecret(String mfaSecret) {
        this.mfaSecret = mfaSecret;
    }
}