package com.mxdui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class ShamirSecretSharing {

    private SecureRandom random = new SecureRandom();
    private static final BigInteger MODULUS = new BigInteger(
            "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141", 16);

    /**
     * Creates shares from a secret using Shamir's Secret Sharing scheme.
     * 
     * @param secret The secret to be shared.
     * @param n      The total number of shares to be created.
     * @param t      The minimum number of shares needed to reconstruct the secret.
     * @return A list of shares, each represented as a pair of BigIntegers.
     * @throws IllegalArgumentException If the total number of shares is less than
     *                                  the threshold.
     */
    public List<BigInteger[]> createShares(BigInteger secret, int n, int t) {
        if (n < t || t < 1) {
            throw new IllegalArgumentException("El número total de partes (n) debe ser mayor o igual al umbral (t).");
        }

        BigInteger[] coefficients = new BigInteger[t];
        coefficients[0] = secret;
        for (int i = 1; i < t; i++) {
            coefficients[i] = new BigInteger(secret.bitLength(), random);
        }

        List<BigInteger[]> shares = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            BigInteger x = BigInteger.valueOf(i);
            BigInteger y = evaluatePolynomial(coefficients, x);
            shares.add(new BigInteger[] { x, y });
        }

        return shares;
    }

    /**
     * Reads shares from a file.
     * 
     * @param filename The name of the file to read from.
     * @return A list of shares, each represented as a pair of BigIntegers.
     * @throws IOException If an error occurs while reading the file.
     */
    public List<BigInteger[]> readShares(String filename) throws IOException {
        List<BigInteger[]> shares = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                BigInteger x = new BigInteger(parts[0]);
                BigInteger y = new BigInteger(parts[1]);
                shares.add(new BigInteger[] { x, y });
            }
        }
        return shares;
    }

    /**
     * Reconstructs the secret from a list of shares.
     * 
     * @param shares The list of shares.
     * @return The reconstructed secret.
     */
    public BigInteger reconstructSecret(List<BigInteger[]> shares) {
        BigInteger secret = BigInteger.ZERO;

        for (int i = 0; i < shares.size(); i++) {
            BigInteger[] share = shares.get(i);
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;

            for (int j = 0; j < shares.size(); j++) {
                if (i != j) {
                    BigInteger xj = shares.get(j)[0];
                    BigInteger xi = shares.get(i)[0];
                    numerator = numerator.multiply(xj.negate());
                    denominator = denominator.multiply(xi.subtract(xj));
                }
            }

            BigInteger term = share[1].multiply(numerator).multiply(modInverse(denominator));
            secret = secret.add(term);
        }

        return secret.mod(MODULUS);
    }

    /**
     * Evaluates a polynomial at a given point.
     * 
     * @param coefficients The coefficients of the polynomial, in increasing order
     *                     of degree.
     * @param x            The point at which to evaluate the polynomial.
     * @return The value of the polynomial at the given point.
     */
    private BigInteger evaluatePolynomial(BigInteger[] coefficients, BigInteger x) {
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < coefficients.length; i++) {
            BigInteger term = coefficients[i].multiply(x.pow(i));
            result = result.add(term);
        }
        return result;
    }

    /**
     * Calculates the modular multiplicative inverse of a number.
     * 
     * @param x The number whose inverse is to be calculated.
     * @return The modular multiplicative inverse of the given number.
     */
    private BigInteger modInverse(BigInteger x) {
        return x.modInverse(MODULUS);
    }
}
