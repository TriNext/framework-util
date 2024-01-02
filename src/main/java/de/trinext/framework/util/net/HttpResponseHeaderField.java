package de.trinext.framework.util.net;

/**
 * A list of common Http-Response-Header fields.
 *
 * @author Seppo
 * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_header_fields#Response_fields">Common Response Headers</a>
 */
@SuppressWarnings({"DuplicateStringLiteralInspection", "unused", "SpellCheckingInspection"})
public enum HttpResponseHeaderField {

    ACCEPT_CH("Accept-CH"),
    ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"),
    ACCESS_CONTROL_ALLOW_CREDENTIALS("Access-Control-Allow-Credentials"),
    ACCESS_CONTROL_EXPOSE_HEADERS("Access-Control-Expose-Headers"),
    ACCESS_CONTROL_MAX_AGE("Access-Control-Max-Age"),
    ACCESS_CONTROL_ALLOW_METHODS("Access-Control-Allow-Methods"),
    ACCESS_CONTROL_ALLOW_HEADERS("Access-Control-Allow-Headers"),
    ACCEPT_PATCH("Accept-Patch"),
    ACCEPT_RANGES("Accept-Ranges"),
    AGE("Age"),
    ALLOW("Allow"),
    ALT_SVC("Alt-Svc"),
    CACHE_CONTROL("Cache-Control"),
    CONNECTION("Connection"),
    CONTENT_DISPOSITION("Content-Disposition"),
    CONTENT_ENCODING("Content-Encoding"),
    CONTENT_LANGUAGE("Content-Language"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_LOCATION("Content-Location"),
    CONTENT_MD5("Content-MD5"),
    CONTENT_RANGE("Content-Range"),
    CONTENT_TYPE("Content-Type"),
    DATE("Date"),
    DELTA_BASE("Delta-Base"),
    ETAG("ETag"),
    EXPIRES("Expires"),
    IM("IM"),
    LAST_MODIFIED("Last-Modified"),
    LINK("Link"),
    LOCATION("Location"),
    P3P("P3P"),
    PRAGMA("Pragma"),
    PREFERENCE_APPLIED("Preference-Applied"),
    PROXY_AUTHENTICATE("Proxy-Authenticate"),
    PUBLIC_KEY_PINS("Public-Key-Pins"),
    RETRY_AFTER("Retry-After"),
    SERVER("Server"),
    SET_COOKIE("Set-Cookie"),
    STRICT_TRANSPORT_SECURITY("Strict-Transport-Security"),
    TRAILER("Trailer"),
    TRANSFER_ENCODING("Transfer-Encoding"),
    TK("Tk"),
    UPGRADE("Upgrade"),
    VARY("Vary"),
    VIA("Via"),
    WARNING("Warning"),
    WWW_AUTHENTICATE("WWW-Authenticate"),
    X_FRAME_OPTIONS("X-Frame-Options"),
    CONTENT_SECURITY_POLICY("Content-Security-Policy"),
    X_CONTENT_SECURITY_POLICY("X-Content-Security-Policy"),
    X_WEBKIT_CSP("X-WebKit-CSP"),
    EXPECT_CT("Expect-CT"),
    NEL("NEL"),
    PERMISSIONS_POLICY("Permissions-Policy"),
    REFRESH("Refresh"),
    REPORT_TO("Report-To"),
    STATUS("Status"),
    TIMING_ALLOW_ORIGIN("Timing-Allow-Origin"),
    X_CONTENT_DURATION("X-Content-Duration"),
    X_CONTENT_TYPE_OPTIONS("X-Content-Type-Options"),
    X_POWERED_BY("X-Powered-By"),
    X_REDIRECT_BY("X-Redirect-By"),
    X_REQUEST_ID("X-Request-ID"),
    X_CORRELATION_ID("X-Correlation-ID"),
    X_UA_COMPATIBLE("X-UA-Compatible"),
    X_XSS_PROTECTION("X-XSS-Protection");

    private final String header;

    HttpResponseHeaderField(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

}