package framework.util.net;


import de.trinext.framework.util.net.HttpBuilder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

final class HttpBuilderTest {

    private static final String VALID_URL = "https://www.example.com/status";
    private static final String INVALID_URL = "|";

    @Test
    void test_for_url_with_string_should_create_with_url_instance() {
        var url = VALID_URL;
        var result = HttpBuilder.forUrl(url);
        assertNotNull(result);
        // Further assertions for HttpBuilderWithUrl if it has methods to retrieve its internal state
    }

    @Test
    void test_for_url_with_u_r_i_should_create_with_url_instance() {
        var uri = URI.create(VALID_URL);
        var result = HttpBuilder.forUrl(uri);
        assertNotNull(result);
        // Further assertions for HttpBuilderWithUrl if it has methods to retrieve its internal state
    }

    @Test
    void test_for_url_with_null_should_throw_exception() {
        assertThrows(NullPointerException.class, () -> {
            HttpBuilder.forUrl((URI) null);
        }, "Passing a null URI should throw a NullPointerException.");
    }

    @Test
    void test_http_builder_instance() {
        assertNotNull(HttpBuilder.DEFAULT_CLIENT, "Default client should be instantiated");
        // Further checks can be performed based on how `HttpBuilder` is used
    }

    @Test
    void test_http_builder_with_url_construction_with_valid_url() {
        var httpBuilderWithUrl = HttpBuilder.forUrl(VALID_URL);
        assertNotNull(httpBuilderWithUrl, "HttpBuilderWithUrl instance sollte nicht null null sein");
        // More assertions to check the state of httpBuilderWithUrl if possible
    }

    @Test
    void test_http_builder_with_url_construction_with_invalid_url() {
        assertThrows(IllegalArgumentException.class, () -> HttpBuilder.forUrl(INVALID_URL), "Invalide URL");
    }
}