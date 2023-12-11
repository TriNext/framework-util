package framework.util;

import javax.crypto.spec.SecretKeySpec;

import de.trinext.framework.util.EncryptionHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EncryptionHelper}.
 *
 * @author ErikBannasch
 */
class EncryptionHelperTest {

    private static final String TEST_KEY = "this is a test key";
    private static final String PLAINTEXT = "Hello World!";
    private static final String ENCODED_TEXT = "bJuA3oHbYjx9dYJ5Sfk1AA==";

    @Test
    void test_generate_key() {
        SecretKeySpec key = EncryptionHelper.generateKey(TEST_KEY);

        Assertions.assertNotNull(key);
        Assertions.assertEquals("AES", key.getAlgorithm());
        Assertions.assertEquals(16, key.getEncoded().length);
    }

    @Test
    void test_encrypt_and_decrypt() {
        SecretKeySpec key = EncryptionHelper.generateKey(TEST_KEY);

        String encryptedText = EncryptionHelper.encrypt(PLAINTEXT, key);
        String decryptedText = EncryptionHelper.decrypt(encryptedText, key);

        Assertions.assertEquals(PLAINTEXT, decryptedText);
    }

    @Test
    void test_encrypt_with_null_plaintext() {
        SecretKeySpec key = EncryptionHelper.generateKey(TEST_KEY);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EncryptionHelper.encrypt(null, key));
    }

    @Test
    void test_decrypt_with_null_encoded_text() {
        SecretKeySpec key = EncryptionHelper.generateKey(TEST_KEY);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EncryptionHelper.decrypt(null, key));
    }

    @Test
    void test_encrypt_with_null_key() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EncryptionHelper.encrypt(PLAINTEXT, null));
    }

    @Test
    void test_decrypt_with_null_key() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EncryptionHelper.decrypt(ENCODED_TEXT, null));
    }

    @Test
    void test_decrypt_with_invalid_encoded_text() {
        SecretKeySpec key = EncryptionHelper.generateKey(TEST_KEY);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EncryptionHelper.decrypt("invalid text", key));
    }

}
