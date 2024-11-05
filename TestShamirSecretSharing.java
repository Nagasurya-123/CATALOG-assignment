package com.mxdui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TestShamirSecretSharing {
    @Test
    void testCreateAndReconstructSecret() {
        ShamirSecretSharing sss = new ShamirSecretSharing();
        BigInteger secret = new BigInteger("1234567890");

        int n = 5;
        int t = 3;
        List<BigInteger[]> shares = sss.createShares(secret, n, t);

        List<BigInteger[]> subsetOfShares = shares.subList(0, t);
        BigInteger reconstructedSecret = sss.reconstructSecret(subsetOfShares);

        assertEquals(secret, reconstructedSecret, "Reconstructed secret should match the original secret.");
    }
}
