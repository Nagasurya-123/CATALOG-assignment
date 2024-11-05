package com.mxdui;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.SecureRandom;

import org.junit.jupiter.api.Test;


public class TestAESCipher {
    @Test
    void testEncryptDecrypt() {
        try {
            String testData = "Hello, World!";
            byte[] testDataBytes = testData.getBytes();

            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);

            byte[] iv = AESCipher.generateIV();

            byte[] encryptedData = AESCipher.encrypt(testDataBytes, key, iv);

            byte[] ivExtracted = new byte[AESCipher.IV_LENGTH];
            System.arraycopy(encryptedData, 0, ivExtracted, 0, AESCipher.IV_LENGTH);

            byte[] encryptedDataOnly = new byte[encryptedData.length - AESCipher.IV_LENGTH];
            System.arraycopy(encryptedData, AESCipher.IV_LENGTH, encryptedDataOnly, 0, encryptedDataOnly.length);

            // Decrypt
            byte[] decryptedData = AESCipher.decrypt(encryptedDataOnly, key, ivExtracted);

            assertArrayEquals(testDataBytes, decryptedData, "Decrypted data should match original data");

        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }
}
