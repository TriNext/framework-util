package framework.util.lang;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import static de.trinext.framework.util.lang.ClassHelper.forceWrapperType;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Dennis Woithe
 */
class ClassHelperTest {

    @Test
    void test_force_wrapper_type() {
        assertEquals(Byte.class, forceWrapperType(byte.class));
        assertEquals(Short.class, forceWrapperType(short.class));
        assertEquals(Integer.class, forceWrapperType(int.class));
        assertEquals(Long.class, forceWrapperType(long.class));
        assertEquals(Boolean.class, forceWrapperType(boolean.class));
        assertEquals(Character.class, forceWrapperType(char.class));
        assertEquals(Double.class, forceWrapperType(double.class));
        assertEquals(Float.class, forceWrapperType(float.class));

        assertEquals(Object.class, forceWrapperType(Object.class));
        assertEquals(Collection.class, forceWrapperType(Collection.class));
    }

}