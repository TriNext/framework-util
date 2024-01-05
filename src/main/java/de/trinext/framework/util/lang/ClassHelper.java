package de.trinext.framework.util.lang;

import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.stream.Stream;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess", "InterfaceNeverImplemented"})
public interface ClassHelper {

    /**
     * Converts a primitive type to its wrapper type.
     */
    static Class<?> forceWrapperType(Class<?> cls) {
        return cls.isPrimitive()
               ? MethodType.methodType(cls).wrap().returnType()
               : cls;
    }

    /**
     * Returns a stream of all fields of the given class and its superclasses regardless of their visibility.
     */
    static Stream<Field> allFieldsOf(Class<?> cls) {
        return Stream.concat(
                Stream.of(cls.getDeclaredFields()),
                cls.getSuperclass() != null ? allFieldsOf(cls.getSuperclass()) : Stream.empty()
        );
    }

}