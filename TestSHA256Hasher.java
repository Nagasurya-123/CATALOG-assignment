package com.mxdui;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

class SHA256HasherTest {

    @Test
    void testHash() {
        try {
            String testData = "Test String";

            byte[] hashedData = SHA256Hasher.hash(testData);

            assertEquals(16, hashedData.length, "Hashed data should be 16 bytes long");

            byte[] hashedDataAgain = SHA256Hasher.hash(testData);
            assertArrayEquals(hashedData, hashedDataAgain, "Hashing the same data should produce the same result");

        } catch (NoSuchAlgorithmException e) {
            fail("NoSuchAlgorithmException should not be thrown");
        }
    }
}
