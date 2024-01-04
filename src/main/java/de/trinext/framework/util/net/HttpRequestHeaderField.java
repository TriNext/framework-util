package de.trinext.framework.util.net;

/**
 * @author Dennis Woithe
 */
public enum HttpRequestHeaderField {
    /**
     * Standard request fields
     */
    A_IM("A-IM"),
    ACCEPT("Accept"),
    ACCEPT_CHARSET("Accept-Charset"),
    ACCEPT_DATETIME("Accept-Datetime"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    ACCESS_CONTROL_REQUEST_METHOD("Access-Control-Request-Method"),
    ACCESS_CONTROL_REQUEST_HEADERS("Access-Control-Request-Headers"),
    AUTHORIZATION("Authorization"),
    CACHE_CONTROL("Cache-Control"),
    CONNECTION("Connection"),
    CONTENT_ENCODING("Content-Encoding"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_MD5("Content-MD5"),
    CONTENT_TYPE("Content-Type"),
    COOKIE("Cookie"),
    DATE("Date"),
    EXPECT("Expect"),
    FORWARDED("Forwarded"),
    FROM("From"),
    HOST("Host"),
    HTTP2_SETTINGS("HTTP2-Settings"),
    IF_MATCH("If-Match"),
    IF_MODIFIED_SINCE("If-Modified-Since"),
    IF_NONE_MATCH("If-None-Match"),
    IF_RANGE("If-Range"),
    IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
    MAX_FORWARDS("Max-Forwards"),
    ORIGIN("Origin"),
    PRAGMA("Pragma"),
    PREFER("Prefer"),
    PROXY_AUTHORIZATION("Proxy-Authorization"),
    RANGE("Range"),
    REFERER("Referer"),
    TE("TE"),
    TRAILER("Trailer"),
    TRANSFER_ENCODING("Transfer-Encoding"),
    USER_AGENT("User-Agent"),
    UPGRADE("Upgrade"),
    VIA("Via"),
    WARNING("Warning"),

    /**
     * Common non-standard request fields
     */
    UPGRADE_INSECURE_REQUESTS("Upgrade-Insecure-Requests"),
    X_REQUESTED_WITH("X-Requested-With"),
    DNT("DNT"),
    X_FORWARDED_FOR("X-Forwarded-For"),
    X_FORWARDED_HOST("X-Forwarded-Host"),
    X_FORWARDED_PROTO("X-Forwarded-Proto"),
    FRONT_END_HTTPS("Front-End-Https"),
    X_HTTP_METHOD_OVERRIDE("X-Http-Method-Override"),
    X_ATT_DEVICEID("X-ATT-DeviceId"),
    X_WAP_PROFILE("X-Wap-Profile"),
    PROXY_CONNECTION("Proxy-Connection"),
    X_UIDH("X-UIDH"),
    X_CSRF_TOKEN("X-Csrf-Token"),
    X_REQUEST_ID("X-Request-ID"),
    X_CORRELATION_ID("X-Correlation-ID"),
    CORRELATION_ID("Correlation-ID"),
    SAVE_DATA("Save-Data"),
    SEC_GPC("Sec-GPC");

    private final String fieldName;

    HttpRequestHeaderField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}

