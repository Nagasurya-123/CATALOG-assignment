package com.mxdui;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AESCipher {

    public static final int IV_LENGTH = 16;

    /**
     * Generates a random initialization vector (IV) for AES encryption.
     * 
     * @return A byte array containing the IV.
     */
    public static byte[] generateIV() {
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    /**
     * Encrypts data using AES with a given key and IV.
     * 
     * @param data The data to encrypt.
     * @param key  The encryption key.
     * @param iv   The initialization vector.
     * @return A byte array containing the IV followed by the encrypted data.
     * @throws Exception If an error occurs during encryption.
     */
    public static byte[] encrypt(byte[] data, byte[] key, byte[] iv) throws Exception {

        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] encryptedData = cipher.doFinal(data);

        byte[] ivAndEncryptedData = new byte[IV_LENGTH + encryptedData.length];
        System.arraycopy(iv, 0, ivAndEncryptedData, 0, IV_LENGTH);
        System.arraycopy(encryptedData, 0, ivAndEncryptedData, IV_LENGTH, encryptedData.length);

        return ivAndEncryptedData;
    }

    /**
     * Decrypts data using AES with a given key and IV.
     * 
     * @param data The data to decrypt.
     * @param key  The decryption key.
     * @param iv   The initialization vector.
     * @return A byte array containing the decrypted data.
     * @throws Exception If an error occurs during decryption.
     */
    public static byte[] decrypt(byte[] data, byte[] key, byte[] iv) throws Exception {

        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        return cipher.doFinal(data);
    }
}
