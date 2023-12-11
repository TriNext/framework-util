package framework.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;

import de.trinext.framework.util.RandomHelper;
import de.trinext.framework.util.encryption.AdvancedEncryptionStandard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link AdvancedEncryptionStandard}.
 *
 * @author ErikBannasch
 */
class AdvancedEncryptionStandardTest {

    private static final String SEED_FOR_TEST_KEY = "this is a test key";
    private static final String PLAINTEXT = "Hello World!";
    private static final String ENCODED_TEXT = "bJuA3oHbYjx9dYJ5Sfk1AA==";

    @Test
    void test_generate_key() {
        var aes = new AdvancedEncryptionStandard(StandardCharsets.UTF_8);
        var key = aes.generateKey(SEED_FOR_TEST_KEY);
        Assertions.assertNotNull(key);
        Assertions.assertEquals("AES", key.getAlgorithm());
        Assertions.assertEquals(16, key.getEncoded().length);
    }

    @Test
    void test_encrypt_and_decrypt() throws InvalidKeyException {
        var aes = new AdvancedEncryptionStandard(StandardCharsets.UTF_8);
        var key = aes.generateKey(SEED_FOR_TEST_KEY);
        var encryptedText = aes.encrypt(PLAINTEXT, key);
        var decryptedText = aes.decrypt(encryptedText, key);

        Assertions.assertEquals(PLAINTEXT, decryptedText);
    }

    @Test
    void test_encrypt_with_null_plaintext() {
        var aes = new AdvancedEncryptionStandard(StandardCharsets.UTF_8);
        var key = aes.generateKey(SEED_FOR_TEST_KEY);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> aes.encrypt(null, key));
    }

    @Test
    void test_decrypt_with_null_encoded_text() {
        var aes = new AdvancedEncryptionStandard(StandardCharsets.UTF_8);
        var key = aes.generateKey(SEED_FOR_TEST_KEY);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> aes.decrypt(null, key));
    }

    @Test
    void test_encrypt_with_null_key() {
        var aes = new AdvancedEncryptionStandard(StandardCharsets.UTF_8);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> aes.encrypt(PLAINTEXT, null));
    }

    @Test
    void test_decrypt_with_null_key() {
        var aes = new AdvancedEncryptionStandard(StandardCharsets.UTF_8);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> aes.decrypt(ENCODED_TEXT, null));
    }

    @Test
    void test_decrypt_with_invalid_encoded_text() {
        var aes = new AdvancedEncryptionStandard(StandardCharsets.UTF_8);
        var key = aes.generateKey(SEED_FOR_TEST_KEY);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> aes.decrypt(RandomHelper.randomString(5), key));
    }

}