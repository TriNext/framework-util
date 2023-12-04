package util;

import java.util.HashMap;

import de.trinext.util.Env;
import de.trinext.util.EnvHelper;
import org.junit.jupiter.api.*;

import static de.trinext.util.RandomHelper.randomWord;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dennis Woithe
 */
final class EnvTest {

    private record EnvTuple(
            String key,
            String value
    ) { }

    private static final EnvTuple ENTRY_EXISTS = new EnvTuple(
            randomWord(5),
            randomWord(5)
    );

    private static final EnvTuple ENTRY_NOT_EXISTS = new EnvTuple(
            randomWord(5),
            randomWord(5)
    );

    @BeforeEach
    void setUp() {
        var vars = new HashMap<String, String>();
        vars.put(ENTRY_EXISTS.key(), ENTRY_EXISTS.value());
        EnvHelper.setEnv(vars);
    }

    @AfterEach
    void tearDown() {
        EnvHelper.setEnv(EnvHelper.ORIGINAL_ENVIRONMENT);
    }

    @Test
    void test_expect() {
        Env.VARIABLES.expect(ENTRY_EXISTS.key());
        assertEquals(ENTRY_EXISTS.value(), System.getenv(ENTRY_EXISTS.key()));
        assertNull(System.getenv(randomWord(3)));
    }

    @Test
    void test_expect_or() {
        Env.VARIABLES.expectOr(ENTRY_NOT_EXISTS.key(), ENTRY_NOT_EXISTS.value());
        assertEquals(ENTRY_NOT_EXISTS.value(), System.getenv(ENTRY_NOT_EXISTS.key()));
    }

}