/**
 * Module info for the util-tests.
 * //@formatter:off
 * @author Dennis Woithe
 */
module framework.util.test {
    requires de.trinext.framework.util;
    requires org.junit.jupiter.api;
    opens framework.util.env to org.junit.platform.commons;
    opens framework.util.lang to de.trinext.framework.util, org.junit.platform.commons;
}