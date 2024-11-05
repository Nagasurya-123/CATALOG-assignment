package com.mxdui;

import java.math.BigInteger;

public class Polynomial {
    private BigInteger[] coefficients;

    /**
     * Constructs a Polynomial with the given coefficients.
     * 
     * @param coefficients The coefficients of the polynomial.
     */
    public Polynomial(BigInteger[] coefficients) {
        this.coefficients = coefficients;
    }

    /**
     * Evaluates the polynomial at the given point.
     * 
     * @param x The point at which to evaluate the polynomial.
     * @return The value of the polynomial at the given point.
     */
    public BigInteger evaluate(BigInteger x) {
        BigInteger result = BigInteger.ZERO;

        for (int i = coefficients.length - 1; i >= 0; i--) {
            result = coefficients[i].add(result.multiply(x));
        }

        return result;
    }
}
