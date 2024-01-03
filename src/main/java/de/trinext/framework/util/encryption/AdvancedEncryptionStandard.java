package de.trinext.framework.util.encryption;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

import static java.security.MessageDigest.getInstance;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public record AdvancedEncryptionStandard(Charset charset) {

    private static final String ALGORITHM = "AES";


    /** Generate a 256-bit key using SHA-256 hashing. */
    public Key generateKey(String seed) {
        if (seed == null || seed.isEmpty())
            throw new IllegalArgumentException("seed must not be null or empty");
        try {
            final var blockSize = 16;
            return new SecretKeySpec(Arrays.copyOf(getInstance("SHA-256").digest(seed.getBytes(charset)), blockSize), ALGORITHM);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError(noSuchAlgorithmException);
        }
    }

    /** Encrypt the plaintext using AES. */
    public String encrypt(String plainText, Key key) throws InvalidKeyException {
        checkArgs(plainText, key);
        var cipher = getCipherInstance();
        cipher.init(Cipher.ENCRYPT_MODE, key);
        try {
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(charset)));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new InvalidKeyException(e);
        }
    }

    /** Decrypt the encryptedText using AES. */
    public String decrypt(String encryptedText, Key key) throws InvalidKeyException {
        checkArgs(encryptedText, key);
        var cipher = getCipherInstance();
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            var decryptedText = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decryptedText, charset);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new InvalidKeyException(e);
        }
    }

    /**
     * The arguments of encrypt and decrypt have to be non-null and non-empty.
     */
    private static void checkArgs(CharSequence text, Key key) throws IllegalArgumentException {
        if (text == null || text.isEmpty())
            throw new IllegalArgumentException("text must not be null or empty");
        if (key == null)
            throw new IllegalArgumentException("key must not be null");
    }

    /** Create a cipher instance. */
    private static Cipher getCipherInstance() {
        try {
            return Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new AssertionError(e);
        }
    }

}