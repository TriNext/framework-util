package de.trinext.framework.util.env;

import java.util.*;

import de.trinext.framework.util.internal.annotations.EvilShitFuckery;

import static de.trinext.framework.util.internal.annotations.EvilShitFuckery.BlackMagic.*;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess", "DuplicateStringLiteralInspection"})
public final class EnvHelper {

    private EnvHelper() {
        throw new AssertionError();
    }

    /**
     * The environment variables passed to the JVM at startup.
     */
    @SuppressWarnings({"StaticCollection", "PublicStaticCollectionField"})
    public static final Map<String, String> ORIGINAL_ENVIRONMENT = Map.copyOf(System.getenv());

    private static final String PROCESS_ENVIRONMENT_CLASS = "java.lang.ProcessEnvironment";

    /**
     * Overwrites the environment variables passed to the JVM at startup.
     * This should only be used in tests.
     * Use {@code EnvHelper.setEnv(EnvHelper.ORIGINAL_ENVIRONMENT)} to reset after the test.
     * Modifies the behaviour of:
     * <ul>
     *     <li>{@link System#getenv()}</li>
     *     <li>{@link System#getenv(String)}}</li>
     * </ul>
     *
     * @throws IllegalStateException When breaking eventually.
     */
    @EvilShitFuckery(blackMagic = {MODIFIES_FINAL_FIELD, MODIFIES_PRIVATE_FIELD, MODIFIES_JVM_BEHAVIOUR, MODIFIES_IMMUTABLE_STRUCT})
    @SuppressWarnings({"SpellCheckingInspection", "OverlyLongMethod", "NestedTryStatement", "OverlyNestedMethod"})
    public static void setEnv(Map<String, String> newEnvVars) {
        try {
            try {
                var processEnvironmentClass = Class.forName(PROCESS_ENVIRONMENT_CLASS);
                var theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
                theEnvironmentField.setAccessible(true);
                var env = (Map<String, String>) theEnvironmentField.get(null);
                env.clear();
                env.putAll(newEnvVars);
                var theCaseInsensitiveEnvField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
                theCaseInsensitiveEnvField.setAccessible(true);
                var theCaseInsensitiveEnvVal = (Map<String, String>) theCaseInsensitiveEnvField.get(null);
                theCaseInsensitiveEnvVal.clear();
                theCaseInsensitiveEnvVal.putAll(newEnvVars);
            } catch (NoSuchFieldException e) {
                var classes = Collections.class.getDeclaredClasses();
                var env = System.getenv();
                for (var cls : classes) {
                    if ("java.util.Collections$UnmodifiableMap".equals(cls.getName())) {
                        var field = cls.getDeclaredField("m");
                        field.setAccessible(true);
                        var obj = field.get(env);
                        var map = (Map<String, String>) obj;
                        map.clear();
                        map.putAll(newEnvVars);
                    }
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Adds an environment to this run of the JVM.
     *
     * @see #setEnv(Map)
     */
    @EvilShitFuckery(blackMagic = {MODIFIES_FINAL_FIELD, MODIFIES_PRIVATE_FIELD, MODIFIES_JVM_BEHAVIOUR, MODIFIES_IMMUTABLE_STRUCT})
    public static void addEnvVariable(String key, String value) {
        if (key == null || key.isBlank())
            throw new IllegalArgumentException("Env-Variable key must not be null or blank");
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Default value must not be null or blank");
        var vars = new HashMap<>(System.getenv());
        vars.put(key, value);
        setEnv(vars);
    }

}