package com.mxdui;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SHA256Hasher {
    /**
     * Hashes the input data using SHA-256 and returns the first 16 bytes of the
     * hash.
     * 
     * @param data The string to be hashed.
     * @return A byte array containing the first 16 bytes of the hash.
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available.
     */
    public static byte[] hash(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] fullHash = md.digest(data.getBytes());
        return Arrays.copyOf(fullHash, 16);
    }
}
