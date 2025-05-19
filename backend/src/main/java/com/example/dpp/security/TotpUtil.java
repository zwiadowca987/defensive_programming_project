package com.example.dpp.security;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TotpUtil {
    // RFC 6238 - długość czasu, przez jaki kod TOTP jest ważny (30 sekund)
    private static final int TIME_STEP_SECONDS = 30;
    // Algorytm HMAC wykorzystywany do generowania kodów TOTP
    private static final String HMAC_ALGORITHM = "HmacSHA1";
    // Domyślna długość kodu TOTP (6 cyfr)
    private static final int CODE_DIGITS = 6;
    // Maksymalna dopuszczalna rozbieżność czasu w liczbie kroków czasowych
    private static final int TIME_SKEW = 1;

    /**
     * Generuje losowy sekret dla TOTP.
     *
     * @return Sekret w formacie Base32
     */
    public static String generateSecret() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20]; // 160 bitów
        random.nextBytes(bytes);
        return Base32Encoder.encode(bytes);
    }

    /**
     * Generuje kod TOTP dla podanego sekretu i przedziału czasowego.
     *
     * @param secret   Sekret w formacie Base32
     * @param timeStep Przedział czasowy
     * @return Wygenerowany kod TOTP
     */
    public static String generateCode(String secret, long timeStep) {
        byte[] decodedSecret = Base32Encoder.decode(secret);
        byte[] data = longToBytes(timeStep);
        // Generacja HMAC-SHA1
        byte[] hash = generateHMAC(decodedSecret, data);
        // Dynamic truncation
        int offset = hash[hash.length - 1] & 0xf;
        int binary = ((hash[offset] & 0x7f) << 24) |
                ((hash[offset + 1] & 0xff) << 16) |
                ((hash[offset + 2] & 0xff) << 8) |
                (hash[offset + 3] & 0xff);
        // Konwersja do kodu numerycznego
        int otp = binary % (int) Math.pow(10, CODE_DIGITS);
        // Uzupełnienie zerami z przodu
        return String.format("%0" + CODE_DIGITS + "d", otp);
    }

    /**
     * Weryfikuje kod TOTP.
     *
     * @param secret Sekret w formacie Base32
     * @param code   Kod TOTP do weryfikacji
     * @return true jeśli kod jest poprawny, false w przeciwnym przypadku
     */
    public static boolean verifyCode(String secret, String code) {
        long currentTimeStep = getCurrentTimeStep();
        // Sprawdzenie aktualnego przedziału czasowego oraz sąsiednich
        for (int i = -TIME_SKEW; i <= TIME_SKEW; i++) {
            String generatedCode = generateCode(secret, currentTimeStep +
                    i);
            if (generatedCode.equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generuje URI dla aplikacji uwierzytelniającej (np. Google
     * Authenticator).
     *
     * @param issuer   Nazwa wydawcy (zwykle nazwa aplikacji)
     * @param username Nazwa użytkownika
     * @param secret   Sekret w formacie Base32
     * @return URI dla aplikacji uwierzytelniającej
     */
    public static String generateTotpUri(String issuer, String username,
                                         String secret) {
        return
                String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s&algorithm=SHA1&digits=%d&period=%d",
                        urlEncode(issuer), urlEncode(username), secret,
                        urlEncode(issuer), CODE_DIGITS, TIME_STEP_SECONDS);
    }


    public static String generateQrCode(String uri) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new
                    EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.ERROR_CORRECTION,
                    ErrorCorrectionLevel.M);
            hints.put(EncodeHintType.MARGIN, 2);
            BitMatrix bitMatrix = qrCodeWriter.encode(
                    uri,
                    BarcodeFormat.QR_CODE,
                    200, // szerokość
                    200, // wysokość
                    hints
            );
            ByteArrayOutputStream outputStream = new
                    ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG",
                    outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            return "data:image/png;base64," +
                    Base64.getEncoder().encodeToString(imageBytes);
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Błąd podczas generowania kodu QR", e);
        }
    }


    private static long getCurrentTimeStep() {
        long currentTimeMillis = System.currentTimeMillis();
        long timeStepMillis =
                TimeUnit.SECONDS.toMillis(TIME_STEP_SECONDS);
        return currentTimeMillis / timeStepMillis;
    }

    private static byte[] longToBytes(long value) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (value & 0xff);
            value >>= 8;
        }
        return result;
    }

    /**
     * Generuje HMAC dla podanego klucza i danych.
     *
     * @param key  Klucz
     * @param data Dane
     * @return HMAC
     */
    private static byte[] generateHMAC(byte[] key, byte[] data) {
        try {
            Mac hmac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(key,
                    HMAC_ALGORITHM);
            hmac.init(keySpec);
            return hmac.doFinal(data);
        } catch (GeneralSecurityException e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    /**
     * Enkoduje URL zgodnie ze standardem RFC 3986.
     *
     * @param input Tekst do enkodowania
     * @return Enkodowany tekst
     */
    private static String urlEncode(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (isUrlSafe(c)) {
                result.append(c);
            } else {
                result.append('%');
                result.append(String.format("%02X", (int) c));
            }
        }
        return result.toString();
    }

    /**
     * Sprawdza, czy znak jest bezpieczny w URL.
     *
     * @param c Znak do sprawdzenia
     * @return true jeśli znak jest bezpieczny, false w przeciwnym
     * przypadku
     */
    private static boolean isUrlSafe(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || ('0'
                <= c && c <= '9')
                || c == '-' || c == '.' || c == '_' || c == '~';
    }
}