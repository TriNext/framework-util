package de.trinext.util;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "DuplicateStringLiteralInspection"})
public final class Env {

    public static final Env VARIABLES = new Env();

    private Env() { }

    public Env expect(String key) {
        if (key == null || key.isBlank())
            throw new IllegalArgumentException("Env-Variable key must not be null or blank");
        var value = System.getenv(key);
        if (value == null || value.isBlank())
            throw new IllegalStateException("Env-Variable " + key + " is not set");
        return this;
    }

    public Env expectOr(String key, String defaultValue) {
        if (key == null || key.isBlank())
            throw new IllegalArgumentException("Env-Variable key must not be null or blank");
        if (defaultValue == null || defaultValue.isBlank())
            throw new IllegalArgumentException("Default value must not be null or blank");
        var value = System.getenv(key);
        if (value == null || value.isBlank())
            EnvHelper.addEnvVariable(key, defaultValue);
        return this;
    }

}