package de.trinext.framework.util.lang;

import java.lang.invoke.MethodType;

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

}