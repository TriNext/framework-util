package de.trinext.framework.util.net;

import java.net.URI;
import java.net.http.*;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest.*;
import java.net.http.HttpResponse.BodyHandler;
import java.time.Duration;
import java.util.*;
import java.util.function.Supplier;

/**
 * A basic HTTP-Builder.
 * <pre>
 * {@code
 * HttpBuilder.forUrl(new UrlQueryBuilder("https://www.example.com", "/status")
 *         .param("personal", true)
 *         .param("query", "all")
 *         .build())
 *     .addHeader("Accept", "application/json")
 *     .addHeader("Authorization", "Bearer " + System.getenv("TEST_TOKEN"))
 *     .get()
 *     .expectBody(BodyHandlers.ofString())
 *     .throwIfCode(404, () -> new IllegalStateException("Example.com not found!"))
 *     .setClient(HttpBuilder.DEFAULT_CLIENT)
 *     .send()
 *     .ifPresent(System.out::println);
 * }
 * </pre>
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess", "PublicInnerClass"})
public final class HttpBuilder {

    public static final HttpClient DEFAULT_CLIENT = HttpClient.newBuilder()
            .followRedirects(Redirect.ALWAYS)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private HttpBuilder() { }

    public static HttpBuilderWithUrl forUrl(String url) {
        return forUrl(URI.create(url));
    }

    public static HttpBuilderWithUrl forUrl(URI url) {
        Objects.requireNonNull(url, "Url must not be null");
        return new HttpBuilderWithUrl(url);
    }

    public static class HttpBuilderWithUrl {

        private final URI myUrl;

        private final Map<String, String> myHeaders = new HashMap<>();

        HttpBuilderWithUrl(URI uri) {
            myUrl = uri;
        }

        public HttpBuilderWithUrl addHeader(String key, String value) {
            if (key == null || key.isBlank())
                throw new IllegalArgumentException("Header key must not be null or blank");
            if (value == null || value.isBlank())
                throw new IllegalArgumentException("Header value for \"" + key + "\" must not be null or blank");
            if (myHeaders.containsKey(key))
                throw new IllegalArgumentException("Header key " + key + " already exists");
            myHeaders.put(key, value);
            return this;
        }

        public HttpBuilderWithUrl addHeaders(Map<String, String> headers) {
            Objects.requireNonNull(headers, "Headers must not be null")
                    .forEach(this::addHeader);
            return this;
        }

        public HttpBuilderRequest get() {
            return fromBuilder(HttpRequest.newBuilder(myUrl).GET());
        }

        public HttpBuilderRequest post(BodyPublisher bodyPublisher) {
            return fromBuilder(HttpRequest.newBuilder(myUrl).POST(bodyPublisher));
        }

        public HttpBuilderRequest delete(BodyPublisher bodyPublisher) {
            return fromBuilder(HttpRequest.newBuilder(myUrl).DELETE());
        }

        public HttpBuilderRequest put(BodyPublisher bodyPublisher) {
            return fromBuilder(HttpRequest.newBuilder(myUrl).PUT(bodyPublisher));
        }

        public HttpBuilderRequest withMethod(HttpMethod method) {
            return withMethod(method, BodyPublishers.noBody());
        }

        public HttpBuilderRequest withMethod(HttpMethod method, BodyPublisher bodyPublisher) {
            return fromBuilder(HttpRequest.newBuilder(myUrl).method(method.name(), bodyPublisher));
        }

        private HttpBuilderRequest fromBuilder(Builder builder) {
            myHeaders.forEach(builder::header);
            return new HttpBuilderRequest(builder.build());
        }

    }

    @SuppressWarnings("ClassCanBeRecord")
    public static class HttpBuilderRequest {

        private final HttpRequest request;

        HttpBuilderRequest(HttpRequest request) {
            this.request = request;
        }

        public <T> HttpBuilderRequestWithBody<T> expectBody(BodyHandler<T> bodyHandler) {
            return new HttpBuilderRequestWithBody<>(request, bodyHandler);
        }

    }

    public static class HttpBuilderRequestWithBody<T> {

        private HttpClient client = DEFAULT_CLIENT;
        private final HttpRequest request;
        private final BodyHandler<T> bodyHandler;

        private final Map<Integer, Supplier<? extends RuntimeException>> codeExceptions = new HashMap<>();

        public HttpBuilderRequestWithBody(HttpRequest request, BodyHandler<T> bodyHandler) {
            this.request = Objects.requireNonNull(request, "HttpRequest must not be null");
            this.bodyHandler = Objects.requireNonNull(bodyHandler, "BodyHandler must not be null");
        }


        public HttpBuilderRequestWithBody<T> setClient(HttpClient httpClient) {
            client = Objects.requireNonNull(httpClient, "HttpClient must not be null");
            return this;
        }

        public HttpBuilderRequestWithBody<T> throwIfCode(int code, Supplier<? extends RuntimeException> exceptionSupplier) {
            codeExceptions.put(code, Objects.requireNonNull(exceptionSupplier, "Exception supplier must not be null"));
            return this;
        }

        public Optional<T> send() {
            HttpResponse<T> response;
            try {
                var myClient = client == null ? DEFAULT_CLIENT : client;
                response = myClient.send(request, bodyHandler);
            } catch (@SuppressWarnings("OverlyBroadCatchBlock") Exception e) {
                return Optional.empty();
            }
            if (codeExceptions.containsKey(response.statusCode()))
                throw codeExceptions.get(response.statusCode()).get();
            return Optional.ofNullable(response.body());
        }

    }

}