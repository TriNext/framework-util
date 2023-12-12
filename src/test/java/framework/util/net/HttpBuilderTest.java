package framework.util.net;


import java.net.URI;

import de.trinext.framework.util.net.HttpBuilder;
import de.trinext.framework.util.net.HttpBuilder.HttpBuilderWithUrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpBuilderTest {

    @BeforeEach
    public void setUp() {
        // Wird noch vorher was gemacht? @Dennis
    }

    @Test
    public void testForUrlWithStringShouldCreateWithUrlInstance() {
        String url = "https://www.example.com/status";
        HttpBuilderWithUrl result = HttpBuilder.forUrl(url);
        assertNotNull(result, "The result should not be null.");
        // Further assertions for HttpBuilderWithUrl if it has methods to retrieve its internal state
    }

    @Test
    public void testForUrlWithURIShouldCreateWithUrlInstance() {
        URI uri = URI.create("https://www.example.com/status");
        HttpBuilderWithUrl result = HttpBuilder.forUrl(uri);
        assertNotNull(result, "The result should not be null.");
        // Further assertions for HttpBuilderWithUrl if it has methods to retrieve its internal state
    }

    @Test
    public void testForUrlWithNullShouldThrowException() {
        assertThrows(NullPointerException.class, () -> {
            HttpBuilder.forUrl((URI) null);
        }, "Passing a null URI should throw a NullPointerException.");
    }

    @Test
    public void testHttpBuilderInstance() {
        assertNotNull(HttpBuilder.DEFAULT_CLIENT, "Default client should be instantiated");
        // Further checks can be performed based on how `HttpBuilder` is used
    }

    @Test
    public void testHttpBuilderWithUrlConstructionWithValidUrl() {
        String url = "https://www.example.com";
        HttpBuilderWithUrl httpBuilderWithUrl = HttpBuilder.forUrl(url);
        assertNotNull(httpBuilderWithUrl, "HttpBuilderWithUrl instance sollte nicht null null sein");
        // More assertions to check the state of httpBuilderWithUrl if possible
    }

    @Test
    public void testHttpBuilderWithUrlConstructionWithInvalidUrl() {
        String url = "invalid   url";
        assertThrows(IllegalArgumentException.class, () -> HttpBuilder.forUrl(url), "Invalide URL");
    }
}