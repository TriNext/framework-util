/**
 * Module info for the util module.
 * //@formatter:off
 * @author Dennis Woithe
 */
module de.trinext.framework.util {
    exports de.trinext.framework.util;
    exports de.trinext.framework.util.lang;
    exports de.trinext.framework.util.net;
    exports de.trinext.framework.util.encryption;
    exports de.trinext.framework.util.env;
    requires jdk.unsupported;
    requires java.net.http;
}