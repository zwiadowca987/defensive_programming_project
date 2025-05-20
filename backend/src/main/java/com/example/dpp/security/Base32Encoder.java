package com.example.dpp.security;

/**
 * Implementacja kodera/dekodera Base32 zgodna ze standardem RFC 4648.
 */
public class Base32Encoder {
    private static final String ALPHABET =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    private static final int BITS_PER_CHAR = 5;
    private static final int MASK = 0x1F; // 0b11111

    /**
     * Enkoduje tablicę bajtów do formatu Base32.
     *
     * @param data Dane do zakodowania
     * @return Dane zakodowane w Base32
     */
    public static String encode(byte[] data) {
        if (data == null || data.length == 0) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        int buffer = 0;
        int bitsLeft = 0;
        for (byte b : data) {
            buffer = (buffer << 8) | (b & 0xff);
            bitsLeft += 8;
            while (bitsLeft >= BITS_PER_CHAR) {
                bitsLeft -= BITS_PER_CHAR;
                result.append(ALPHABET.charAt((buffer >> bitsLeft) &
                        MASK));
            }
        }
        if (bitsLeft > 0) {
            buffer = buffer << (BITS_PER_CHAR - bitsLeft);
            result.append(ALPHABET.charAt(buffer & MASK));
        }
        return result.toString();
    }

    /**
     * Dekoduje ciąg znaków Base32 do tablicy bajtów.
     *
     * @param data Dane do zdekodowania
     * @return Zdekodowane dane
     */
    public static byte[] decode(String data) {
        if (data == null || data.isEmpty()) {
            return new byte[0];
        }
        data = data.toUpperCase().replaceAll("[^A-Z2-7]", "");
        int outputLength = (data.length() * BITS_PER_CHAR) / 8;
        byte[] result = new byte[outputLength];
        int buffer = 0;
        int bitsLeft = 0;
        int byteIndex = 0;
        for (char c : data.toCharArray()) {
            int value = ALPHABET.indexOf(c);
            if (value < 0) {
                continue; // Ignoruj nieznane znaki
            }
            buffer = (buffer << BITS_PER_CHAR) | value;
            bitsLeft += BITS_PER_CHAR;
            if (bitsLeft >= 8) {
                bitsLeft -= 8;
                result[byteIndex++] = (byte) ((buffer >> bitsLeft) &
                        0xff);
            }
        }
        return result;
    }
}
