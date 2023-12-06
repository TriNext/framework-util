package framework.net;

import java.net.URI;
import java.util.Map;

import de.trinext.framework.util.net.UrlQueryBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test your utility interfaces here.
 *
 * @author Dennis Woithe
 */
class UrlQueryBuilderTest {

    private static final String BASEURL = "https://www.google.com";
    private static final String ROUTE = "/tonyStark";
    private static final Map<String, String> TESTMAP = Map.of("key1", "value1", "key2", "value2");

    private static final String EXPECTED_STRING = "https://www.google.com/tonyStark?key1=value1&key2=value2";
    private static final URI EXPECTED_URI = URI.create(EXPECTED_STRING);

    @Test void testUrlQueryBuilderWithFirstConstructor() {
        UrlQueryBuilder urlQueryBuilder = new UrlQueryBuilder(BASEURL, ROUTE);
        for (var entry : TESTMAP.entrySet()) {
            urlQueryBuilder.param(entry.getKey(), entry.getValue());
        }
        URI uri = urlQueryBuilder.build();
        assertEquals(EXPECTED_URI, uri);
        String uriString = urlQueryBuilder.toString();
        assertEquals(EXPECTED_STRING, uriString);
    }


    @Test void testUrlQueryBuilderWithSecondConstructor() {
        UrlQueryBuilder urlQueryBuilder = new UrlQueryBuilder(BASEURL, ROUTE, TESTMAP);
        URI uri = urlQueryBuilder.build();
        assertEquals(EXPECTED_URI, uri);
        String uriString = urlQueryBuilder.toString();
        assertEquals(EXPECTED_STRING, uriString);
    }

}