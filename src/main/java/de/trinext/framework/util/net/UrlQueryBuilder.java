package de.trinext.framework.util.net;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings("unused")
public class UrlQueryBuilder {

    private final Map<String, String> params = new HashMap<>();

    private final String route;
    private final String baseUrl;

    public UrlQueryBuilder(String baseUrl, String route) {
        this.route = route;
        this.baseUrl = baseUrl;
    }

    public UrlQueryBuilder(String baseUrl, String route, Map<String, String> params) {
        this.params.putAll(params);
        this.route = route;
        this.baseUrl = baseUrl;
    }

    public final UrlQueryBuilder param(String name, String value) {
        params.put(name, value);
        return this;
    }

    public final UrlQueryBuilder param(String name, Object value) {
        params.put(name, String.valueOf(value));
        return this;
    }

    public final UrlQueryBuilder param(String name, boolean value) {
        return param(name, value ? "1" : "0");
    }

    public final URI build() {
        return URI.create(toString());
    }

    @Override public String toString() {
        //noinspection SimplifiableIfStatement
        if (params.isEmpty()) {
            return baseUrl + route;
        } else {
            return baseUrl + route + params.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&", "?", ""));

        }
    }

}