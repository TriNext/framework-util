package framework.util.lang;

import java.util.Collection;

import de.trinext.framework.util.lang.ClassHelper;
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

    @Test
    void test_all_fields_of() {
        class A {

            private int a;
            int b;

        }

        class B extends A {

            int a;
            int c;

        }

        var fields = ClassHelper.allFieldsOf(B.class).toList();
        assertEquals(4, fields.size());
        assertEquals(2, fields.stream().filter(f -> "a".equals(f.getName())).count());
        assertEquals(1, fields.stream().filter(f -> "b".equals(f.getName())).count());
        assertEquals(1, fields.stream().filter(f -> "c".equals(f.getName())).count());
    }

}