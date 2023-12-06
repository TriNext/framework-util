package de.trinext.framework.util.media;

/**
 * A list of common MIME types used in web applications.
 *
 * @author Dennis Woithe
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types">Common MIME types</a>
 */
@SuppressWarnings({"DuplicateStringLiteralInspection", "unused", "SpellCheckingInspection"})
public enum MimeType {

    AAC(".aac", "audio/aac"),
    ABW(".abw", "application/x-abiword"),
    ARC(".arc", "application/x-freearc"),
    AVIF(".avif", "image/avif"),
    AVI(".avi", "video/x-msvideo"),
    AZW(".azw", "application/vnd.amazon.ebook"),
    BIN(".bin", "application/octet-stream"),
    BMP(".bmp", "image/bmp"),
    BZ(".bz", "application/x-bzip"),
    BZ2(".bz2", "application/x-bzip2"),
    CDA(".cda", "application/x-cdf"),
    CSH(".csh", "application/x-csh"),
    CSS(".css", "text/css"),
    CSV(".csv", "text/csv"),
    DOC(".doc", "application/msword"),
    DOCX(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    EOT(".eot", "application/vnd.ms-fontobject"),
    EPUB(".epub", "application/epub+zip"),
    GZ(".gz", "application/gzip"),
    GIF(".gif", "image/gif"),
    HTM(".htm", "text/html"),
    HTML(".html", "text/html"),
    ICO(".ico", "image/vnd.microsoft.icon"),
    ICS(".ics", "text/calendar"),
    JAR(".jar", "application/java-archive"),
    JPG(".jpg", "image/jpeg"),
    JPEG(".jpeg", "image/jpeg"),
    JS(".js", "text/javascript"),
    JSON(".json", "application/json"),
    JSONLD(".jsonld", "application/ld+json"),
    MID(".mid", "audio/midi, audio/x-midi"),
    MIDI(".midi", "audio/midi, audio/x-midi"),
    MJS(".mjs", "text/javascript"),
    MP3(".mp3", "audio/mpeg"),
    MP4(".mp4", "video/mp4"),
    MPEG(".mpeg", "video/mpeg"),
    MPKG(".mpkg", "application/vnd.apple.installer+xml"),
    ODP(".odp", "application/vnd.oasis.opendocument.presentation"),
    ODS(".ods", "application/vnd.oasis.opendocument.spreadsheet"),
    ODT(".odt", "application/vnd.oasis.opendocument.text"),
    OGA(".oga", "audio/ogg"),
    OGV(".ogv", "video/ogg"),
    OGX(".ogx", "application/ogg"),
    OPUS(".opus", "audio/opus"),
    OTF(".otf", "font/otf"),
    PNG(".png", "image/png"),
    PDF(".pdf", "application/pdf"),
    PHP(".php", "application/x-httpd-php"),
    PPT(".ppt", "application/vnd.ms-powerpoint"),
    PPTX(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    RAR(".rar", "application/vnd.rar"),
    RTF(".rtf", "application/rtf"),
    SH(".sh", "application/x-sh"),
    SVG(".svg", "image/svg+xml"),
    TAR(".tar", "application/x-tar"),
    TIF(".tif", "image/tiff"),
    TIFF(".tiff", "image/tiff"),
    TS(".ts", "video/mp2t"),
    TTF(".ttf", "font/ttf"),
    TXT(".txt", "text/plain"),
    VSD(".vsd", "application/vnd.visio"),
    WAV(".wav", "audio/wav"),
    WEBA(".weba", "audio/webm"),
    WEBM(".webm", "video/webm"),
    WEBP(".webp", "image/webp"),
    WOFF(".woff", "font/woff"),
    WOFF2(".woff2", "font/woff2"),
    XHTML(".xhtml", "application/xhtml+xml"),
    XLS(".xls", "application/vnd.ms-excel"),
    XLSX(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    XML(".xml", "application/xml"),
    XUL(".xul", "application/vnd.mozilla.xul+xml"),
    ZIP(".zip", "application/zip"),
    VIDEO_3GP(".3gp", "video/3gpp"),
    AUDIO_3GP(".3gp", "audio/3gpp"),
    VIDEO_3G2(".3g2", "video/3gpp2"),
    AUDIO_3G2(".3g2", "audio/3gpp2"),
    SEVEN_Z(".7z", "application/x-7z-compressed");

    private final String extension;
    private final String mimeType;

    MimeType(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public String getExtension() {
        return extension;
    }

    public String getMimeType() {
        return mimeType;
    }
}