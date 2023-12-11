package de.trinext.framework.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;


/**
 * @author Erik Bannasch
 */
public final class EncryptionHelper {

    private static final int BIT_KEY = 16;

    public static SecretKeySpec generateKey(String seed) {
        var key = seed.getBytes(StandardCharsets.UTF_8);

        // Generate a 256-bit key using SHA-256 hashing
        MessageDigest sha;
        try {
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, BIT_KEY); // 16 bytes for 128-bit key, change if using different key size
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError(noSuchAlgorithmException);
        }

        // Create a secret key specification using AES algorithm
        return new SecretKeySpec(key, "AES");
    }

    @SuppressWarnings("DuplicateStringLiteralInspection")
    public static String encrypt(String plaintext, Key secretKey) {
        if (plaintext == null || plaintext.isEmpty())
            throw new IllegalArgumentException("plaintext must not be null or empty");
        if (secretKey == null)
            throw new IllegalArgumentException("secretKey must not be null");
        try {
            // Create an AES cipher instance
            var cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Encrypt the plaintext
            var encryptedText = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedText);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException internalError) {
            throw new AssertionError(internalError);
        } catch (InvalidKeyException | RuntimeException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @SuppressWarnings("DuplicateStringLiteralInspection")
    public static String decrypt(String encodedText, Key secretKey) {
        if (encodedText == null || encodedText.isEmpty())
            throw new IllegalArgumentException("encodedText must not be null or empty");
        if (secretKey == null)
            throw new IllegalArgumentException("secretKey must not be null");
        try {
            var encryptedText = Base64.getDecoder().decode(encodedText);
            // Create an AES cipher instance
            var cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Decrypt the ciphertext
            var decryptedText = cipher.doFinal(encryptedText);
            return new String(decryptedText, StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException internalError) {
            throw new AssertionError(internalError);
        } catch (InvalidKeyException | RuntimeException e) {
            throw new IllegalArgumentException(e);
        }
    }

}