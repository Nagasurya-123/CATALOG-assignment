package com.mxdui;

import org.junit.jupiter.api.Test;


import java.math.BigInteger;
import static org.junit.jupiter.api.Assertions.*;

class PolynomialTest {

    @Test
    void testEvaluate() {
        BigInteger[] coefficients = {
                BigInteger.valueOf(1), 
                BigInteger.valueOf(2), 
                BigInteger.valueOf(3) 
        };
        Polynomial polynomial = new Polynomial(coefficients);

        BigInteger x = BigInteger.valueOf(2);
        BigInteger expectedValue = BigInteger.valueOf(17);
        BigInteger actualValue = polynomial.evaluate(x);

        assertEquals(expectedValue, actualValue, "Polynomial evaluation should be correct.");
    }
}
