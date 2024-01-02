package framework.util.lang;

import de.trinext.framework.util.lang.ClassChecker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Dennis Woithe
 */
final class ClassCheckerTest {

    private static final Class<?>[] PRIMITIVE_CLASSES = {
            int.class,
            boolean.class,
            byte.class,
            char.class,
            short.class,
            long.class,
            float.class,
            double.class
    };

    private static final Class<?>[] PRIMITIVE_WRAPPER_CLASSES = {
            Integer.class,
            Boolean.class,
            Byte.class,
            Character.class,
            Short.class,
            Long.class,
            Float.class,
            Double.class
    };

    // ----------------------------------------------------------------------------------------------------

    @SuppressWarnings("All")
    interface TestInterface { }

    @SuppressWarnings("All")
    abstract class AbstractTestClass { }

    @SuppressWarnings("All") final class FinalTestClass { }

    @SuppressWarnings("All")
    static class StaticTestClass { }

    @SuppressWarnings("All")
    enum TestEnum {}

    @SuppressWarnings("All")
    record TestRecord() { }

    @SuppressWarnings("All")
    public class PublicTestClass { }

    @SuppressWarnings("All")
    protected class ProtectedTestClass { }

    @SuppressWarnings("All")
    private class PrivateTestClass { }

    @SuppressWarnings("All")
    sealed class SealedTestClass permits NonSealedTestClass { }

    @SuppressWarnings("All")
    non-sealed class NonSealedTestClass extends SealedTestClass { }

    // ----------------------------------------------------------------------------------------------------

    /** Standard method signature inside the {@link ClassChecker} with the {@link IllegalArgumentException} for {@code <X>} */
    @FunctionalInterface
    private interface ClsCheck<T> {

        void test(ClassChecker<T, IllegalArgumentException> checker) throws IllegalArgumentException;

    }

    @SuppressWarnings("All")
    private static long
            PRIMITIVE = 1 << 0, PRIMITIVE_WRAPPER = 1 << 1,
            ARRAY = 1 << 2,
            ENUM = 1 << 3, RECORD = 1 << 4, INTERFACE = 1 << 5,
            ABSTRACT = 1 << 6, FINAL = 1 << 7,
            PUBLIC = 1 << 8, PRIVATE = 1 << 9, PROTECTED = 1 << 10,
            STATIC = 1 << 11,
            SEALED = 1 << 12;

    /**
     * Executes the passed {@link ClsCheck} for all possible properties a structure could have.
     * The {@code flag}-bit tells whether this method asserts that the {@code check} does not throw an {@link IllegalArgumentException}.
     */
    @SuppressWarnings({"rawtypes", "unchecked", "MethodWithMultipleLoops"})
    private static void assertFor(long flag, ClsCheck<?> clsCheck) {
        var check = (ClsCheck) clsCheck;
        for (var primitiveCls : PRIMITIVE_CLASSES)
            assertThrowWhether((flag & PRIMITIVE) > 0, primitiveCls, check);

        for (var wrapperCls : PRIMITIVE_WRAPPER_CLASSES)
            assertThrowWhether((flag & PRIMITIVE_WRAPPER) > 0, wrapperCls, check);

        assertThrowWhether((flag & ARRAY) > 0, int[].class, check);
        assertThrowWhether((flag & ARRAY) > 0, int[][].class, check);
        assertThrowWhether((flag & ARRAY) > 0, Object[].class, check);
        assertThrowWhether((flag & ARRAY) > 0, Object[][].class, check);

        assertThrowWhether((flag & ENUM) > 0, TestEnum.class, check);
        assertThrowWhether((flag & RECORD) > 0, TestRecord.class, check);
        assertThrowWhether((flag & INTERFACE) > 0, TestInterface.class, check);

        assertThrowWhether((flag & ABSTRACT) > 0, AbstractTestClass.class, check);
        assertThrowWhether((flag & FINAL) > 0, FinalTestClass.class, check);
        assertThrowWhether((flag & STATIC) > 0, StaticTestClass.class, check);

        assertThrowWhether((flag & PUBLIC) > 0, PublicTestClass.class, check);
        assertThrowWhether((flag & PRIVATE) > 0, PrivateTestClass.class, check);
        assertThrowWhether((flag & PROTECTED) > 0, ProtectedTestClass.class, check);

        assertThrowWhether((flag & SEALED) > 0, SealedTestClass.class, check);
    }

    private static <T> void assertThrowWhether(boolean condition, Class<T> cls, ClsCheck<T> check) {
        Executable checkTest = () -> check.test(new ClassChecker<>(cls, IllegalArgumentException::new));
        if (condition) assertDoesNotThrow(
                checkTest,
                "Unexpected IllegalArgumentException for " + cls.getSimpleName()
        );
        else assertThrows(
                IllegalArgumentException.class,
                checkTest,
                "Expected IllegalArgumentException for " + cls.getSimpleName()
        );
    }

    @Test
    void test_primitive() {
        assertFor(PRIMITIVE, ClassChecker::isPrimitive);
        assertFor(~PRIMITIVE, ClassChecker::isNotPrimitive);
    }

    @Test
    void test_array() {
        assertFor(ARRAY, ClassChecker::isArray);
        assertFor(~ARRAY, ClassChecker::isNotArray);
    }

    @Test
    void test_enum() {
        assertFor(ENUM, ClassChecker::isEnum);
        assertFor(~ENUM, ClassChecker::isNotEnum);
    }

    @Test
    void test_record() {
        assertFor(RECORD, ClassChecker::isRecord);
        assertFor(~RECORD, ClassChecker::isNotRecord);
    }

    @Test
    void test_interface() {
        assertFor(INTERFACE, ClassChecker::isInterface);
        assertFor(~INTERFACE, ClassChecker::isNotInterface);
    }

    @Test
    void test_abstract() {
        // primitive classes are weirdly considered abstract:
        // https://stackoverflow.com/questions/13180600/why-are-java-primitive-types-modifiers-public-abstract-and-final
        var abstractStructures = PRIMITIVE | ARRAY | ABSTRACT | INTERFACE;
        assertFor(abstractStructures, ClassChecker::isAbstract);
        assertFor(~abstractStructures, ClassChecker::isNotAbstract);
    }

    @Test
    void test_final() {
        // primitives, wrappers, arrays, enums, records and final classes are final
        var finalStructures = PRIMITIVE | PRIMITIVE_WRAPPER | ARRAY | ENUM | RECORD | FINAL;
        assertFor(finalStructures, ClassChecker::isFinal);
        assertFor(~finalStructures, ClassChecker::isNotFinal);
    }

    @Test
    void test_public() {
        var publicStructures = PRIMITIVE | PRIMITIVE_WRAPPER | ARRAY | PUBLIC;
        assertFor(publicStructures, ClassChecker::isPublic);
        assertFor(~publicStructures, ClassChecker::isNotPublic);
    }

    @Test
    void test_private() {
        assertFor(PRIVATE, ClassChecker::isPrivate);
        assertFor(~PRIVATE, ClassChecker::isNotPrivate);
    }

    @Test
    void test_protected() {
        assertFor(PROTECTED, ClassChecker::isProtected);
        assertFor(~PROTECTED, ClassChecker::isNotProtected);
    }

    @Test
    void test_static() {
        // (inner) enums, records, interfaces and static classes are static
        var staticStructures = ENUM | RECORD | INTERFACE | STATIC;
        assertFor(staticStructures, ClassChecker::isStatic);
        assertFor(~staticStructures, ClassChecker::isNotStatic);
    }

    @Test
    void test_sealed() {
        assertFor(SEALED, ClassChecker::isSealed);
        assertFor(~SEALED, ClassChecker::isNotSealed);
    }

}