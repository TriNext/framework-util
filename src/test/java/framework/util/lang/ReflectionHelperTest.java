package framework.util.lang;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import de.trinext.framework.util.RandomHelper;
import de.trinext.framework.util.lang.ReflectionHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dennis Woithe
 */
final class ReflectionHelperTest {

    record TestRecord(
            byte bt,
            short sht,
            int i,
            long lng,
            float flt,
            double dbl,
            boolean bln,
            char chr,
            Object obj

    )
    { }

    @SuppressWarnings("InstanceVariableMayNotBeInitialized")
    static class TestCls {

        byte bt;
        short sht;
        int i;
        long lng;
        float flt;
        double dbl;
        boolean bln;
        char chr;
        Object obj;

    }

    @Test
    void test_try_get_actual_type_arguments() throws NoSuchFieldException {
        class TestClsWithGenericField {

            List<String> foo;

            String bar;

        }

        var field = TestClsWithGenericField.class.getDeclaredField("foo");
        var optType = ReflectionHelper.tryGetActualTypeArguments(field);
        assertTrue(optType.isPresent());
        assertEquals(1, optType.get().length);
        assertEquals(String.class, optType.get()[0]);

        var field2 = TestClsWithGenericField.class.getDeclaredField("bar");
        var optType2 = ReflectionHelper.tryGetActualTypeArguments(field2);
        assertTrue(optType2.isEmpty());
    }

    @Test
    void test_create_instance_without_constructor() {
        assertEquals(0, ReflectionHelper.createInstanceWithoutConstructor(int.class));
        assertEquals(0L, ReflectionHelper.createInstanceWithoutConstructor(long.class));
        assertEquals(0.0f, ReflectionHelper.createInstanceWithoutConstructor(float.class));
        assertEquals(0.0d, ReflectionHelper.createInstanceWithoutConstructor(double.class));
        assertEquals(false, ReflectionHelper.createInstanceWithoutConstructor(boolean.class));
        assertEquals('\u0000', ReflectionHelper.createInstanceWithoutConstructor(char.class));

        assertEquals(0, ReflectionHelper.createInstanceWithoutConstructor(Integer.class));
        assertEquals(0L, ReflectionHelper.createInstanceWithoutConstructor(Long.class));
        assertEquals(0.0f, ReflectionHelper.createInstanceWithoutConstructor(Float.class));
        assertEquals(0.0d, ReflectionHelper.createInstanceWithoutConstructor(Double.class));
        assertEquals(false, ReflectionHelper.createInstanceWithoutConstructor(Boolean.class));
        assertEquals('\u0000', ReflectionHelper.createInstanceWithoutConstructor(Character.class));

        assertThrows(IllegalArgumentException.class, () -> ReflectionHelper.createInstanceWithoutConstructor(Collection.class));

        assertEquals(new TestRecord(
                (byte) 0,
                (short) 0,
                0,
                0L,
                0.0f,
                0.0d,
                false,
                '\u0000',
                null
        ), ReflectionHelper.createInstanceWithoutConstructor(TestRecord.class));
    }

    @Test
    void test_force_construct_class() {
        var testCls = new TestCls() {{
            bt = RandomHelper.randomByte();
            sht = RandomHelper.randomShort();
            i = RandomHelper.randomInt();
            lng = RandomHelper.randomLong();
            flt = RandomHelper.randomFloat();
            dbl = RandomHelper.randomDouble();
            bln = RandomHelper.randomBool();
            chr = RandomHelper.randomLetter();
            obj = RandomHelper.randomWord(10);
        }};
        ReflectionHelper.forceConstructClass(
                TestCls.class,
                field -> switch (field.getName()) {
                    case "bt" -> testCls.bt;
                    case "sht" -> testCls.sht;
                    case "i" -> testCls.i;
                    case "lng" -> testCls.lng;
                    case "flt" -> testCls.flt;
                    case "dbl" -> testCls.dbl;
                    case "bln" -> testCls.bln;
                    case "chr" -> testCls.chr;
                    case "obj" -> testCls.obj;
                    default -> throw new AssertionError("Unexpected value: " + field);
                }
        );
        ReflectionHelper.forceConstructClass(Object.class, shouldNotBeCalled -> { throw new AssertionError(); });
        assertThrows(
                IllegalArgumentException.class,
                () -> ReflectionHelper.forceConstructClass(List.class, field -> null)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> ReflectionHelper.forceConstructClass(TestRecord.class, field -> null)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> ReflectionHelper.forceConstructClass(TestCls.class, null)
        );
    }

    @Test
    void test_force_construct_record() throws InvocationTargetException, IllegalAccessException {
        var testRecord = new TestRecord(
                RandomHelper.randomByte(),
                RandomHelper.randomShort(),
                RandomHelper.randomInt(),
                RandomHelper.randomLong(),
                RandomHelper.randomFloat(),
                RandomHelper.randomDouble(),
                RandomHelper.randomBool(),
                RandomHelper.randomChar(),
                RandomHelper.randomWord(10)
        );
        assertEquals(testRecord, ReflectionHelper.constructRecord(
                        TestRecord.class,
                        field -> switch (field.getName()) {
                            case "bt" -> testRecord.bt();
                            case "sht" -> testRecord.sht();
                            case "i" -> testRecord.i();
                            case "lng" -> testRecord.lng();
                            case "flt" -> testRecord.flt();
                            case "dbl" -> testRecord.dbl();
                            case "bln" -> testRecord.bln();
                            case "chr" -> testRecord.chr();
                            case "obj" -> testRecord.obj();
                            default -> throw new AssertionError("Unexpected value: " + field);
                        }
                )
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> ReflectionHelper.constructRecord(Object.class, field -> null)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> ReflectionHelper.constructRecord(List.class, field -> null)
        );
    }

}