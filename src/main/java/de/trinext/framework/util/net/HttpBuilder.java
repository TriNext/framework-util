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
 *     .withMethod(HttpMethod.GET)
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

    /**
     * Set the {@link URI} used for the request. For example:
     * <pre>{@code
     * HttpBuilder.forUrl("https://www.example.com/status")
     * }</pre>
     */
    public static HttpBuilderWithUrl forUrl(String url) {
        return forUrl(URI.create(url));
    }

    /**
     * Set the {@link URI} used for the request. For example:
     * <pre>{@code
     * HttpBuilder.forUrl(new UrlQueryBuilder("https://www.example.com", "/status")
     *        .param("personal", true)
     *        .param("query", "all")
     *        .build())
     * }</pre>
     */
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

        /**
         * Add a header to the request.
         *
         * @throws IllegalArgumentException if the key is null or blank, the value is null or blank or the key already exists.
         */
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

        /**
         * Add multiple headers to the request.
         *
         * @see #addHeader(String, String)
         */
        public HttpBuilderWithUrl addHeaders(Map<String, String> headers) {
            Objects.requireNonNull(headers, "Headers must not be null")
                    .forEach(this::addHeader);
            return this;
        }

        /**
         * @deprecated Use {@link #withMethod(HttpMethod)} instead.
         */
        @Deprecated(forRemoval = true)
        public HttpBuilderRequest get() {
            return withMethod(HttpMethod.GET);
        }

        /**
         * @deprecated Use {@link #withMethod(HttpMethod)} instead.
         */
        @Deprecated(forRemoval = true)
        public HttpBuilderRequest post(BodyPublisher bodyPublisher) {
            return withMethod(HttpMethod.POST, bodyPublisher);
        }

        /**
         * @deprecated Use {@link #withMethod(HttpMethod)} instead.
         */
        @Deprecated(forRemoval = true)
        public HttpBuilderRequest delete(BodyPublisher bodyPublisher) {
            return withMethod(HttpMethod.DELETE, bodyPublisher);
        }

        /**
         * @deprecated Use {@link #withMethod(HttpMethod)} instead.
         */
        @Deprecated(forRemoval = true)
        public HttpBuilderRequest put(BodyPublisher bodyPublisher) {
            return withMethod(HttpMethod.PUT, bodyPublisher);
        }

        /**
         * Sets the {@link HttpMethod} used for the request and specifies to not send a body.
         */
        public HttpBuilderRequest withMethod(HttpMethod method) {
            return withMethod(method, BodyPublishers.noBody());
        }

        /**
         * Sets the {@link HttpMethod} used for the request and specifies the body to send.
         */
        public HttpBuilderRequest withMethod(HttpMethod method, BodyPublisher bodyPublisher) {
            return addHeadersTo(HttpRequest.newBuilder(myUrl).method(method.name(), bodyPublisher));
        }

        /**
         * Adds the headers to the {@link Builder} and returns a {@link HttpBuilderRequest}.
         */
        private HttpBuilderRequest addHeadersTo(Builder builder) {
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

        /**
         * Sets the expected body type.
         */
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


        /**
         * Sets the {@link HttpClient} used for the request. If not set, {@link #DEFAULT_CLIENT} is used.
         */
        public HttpBuilderRequestWithBody<T> setClient(HttpClient httpClient) {
            client = Objects.requireNonNull(httpClient, "HttpClient must not be null");
            return this;
        }

        /**
         * Throws the given exception if the response code matches.
         */
        public HttpBuilderRequestWithBody<T> throwIfCode(int code, Supplier<? extends RuntimeException> exceptionSupplier) {
            codeExceptions.put(code, Objects.requireNonNull(exceptionSupplier, "Exception supplier must not be null"));
            return this;
        }

        /**
         * Sends the request and tries to return the body.
         */
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