package de.trinext.framework.util.lang;

import java.lang.reflect.Modifier;
import java.util.function.*;

/**
 * Contains methods to check a class for certain properties.
 * Example:
 * <pre>{@code
 * // Throws an IllegalArgumentException if the given class is not a public, non-final, non-abstract class.
 * new ClassChecker(cls, IllegalArgumentException::new)
 *             .isNotPrimitive()
 *             .isNotArray()
 *             .isNotEnum()
 *             .isNotRecord()
 *             .isNotInterface()
 *             .isNotAbstract()
 *             .isFinal()
 *             .isPublic()
 * }</pre>
 *
 * @param <T> The type to check.
 * @param <X> The type of the exception to throw.
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess", "ClassCanBeRecord", "HardCodedStringLiteral", "DuplicateStringLiteralInspection", "BoundedWildcard"})
public class ClassChecker<T, X extends Throwable> {

    private final Class<T> cls;
    private final Function<String, X> exceptionSupplier;

    public ClassChecker(Class<T> cls, Function<String, X> exceptionSupplier) {
        this.cls = cls;
        this.exceptionSupplier = exceptionSupplier;
    }

    /**
     * Throws an exception if the {@link #cls} doesn't match the given predicate.
     */
    private ClassChecker<T, X> checkIs(Predicate<Class<T>> predicate, String is) throws X {
        return checkIsNot(predicate.negate(), "not " + is);
    }

    /**
     * Throws an exception if the {@link #cls} matches the given predicate.
     */
    private ClassChecker<T, X> checkIsNot(Predicate<Class<T>> predicate, String is) throws X {
        if (predicate.test(cls))
            throw exceptionSupplier.apply(cls.getSimpleName() + " is " + is + ".");
        return this;
    }

    /**
     * Throws an exception if the {@link #cls} doesn't match the given predicate.
     */
    private ClassChecker<T, X> checkHasModifier(IntPredicate predicate, String hasNotModifier) throws X {
        return checkHasNotModifier(predicate.negate(), "not " + hasNotModifier);
    }

    /**
     * Throws an exception if the {@link #cls} matches the given predicate.
     */
    private ClassChecker<T, X> checkHasNotModifier(IntPredicate predicate, String hasModifier) throws X {
        if (predicate.test(cls.getModifiers()))
            throw exceptionSupplier.apply(cls.getSimpleName() + " is " + hasModifier + ".");
        return this;
    }

    /**
     * Checks if the given class is primitive.
     * Throws an exception if not.
     */
    public ClassChecker<T, X> isPrimitive() throws X {
        return checkIs(Class::isPrimitive, "a primitive");
    }

    /**
     * Checks if the given class is not primitive.
     * Throws an exception if it is.
     */
    public ClassChecker<T, X> isNotPrimitive() throws X {
        return checkIsNot(Class::isPrimitive, "a primitive");
    }

    /**
     * Checks if the given class is an array.
     * Throws an exception if not.
     */
    public ClassChecker<T, X> isArray() throws X {
        return checkIs(Class::isArray, "an array");
    }

    /**
     * Checks if the given class is not an array.
     * Throws an exception if it is.
     */
    public ClassChecker<T, X> isNotArray() throws X {
        return checkIsNot(Class::isArray, "an array");
    }

    /**
     * Checks if the given class is an enum.
     * Throws an exception if not.
     */
    public ClassChecker<T, X> isEnum() throws X {
        return checkIs(Class::isEnum, "an enum");
    }

    /**
     * Checks if the given class is not an enum.
     * Throws an exception if it is.
     */
    public ClassChecker<T, X> isNotEnum() throws X {
        return checkIsNot(Class::isEnum, "an enum");
    }

    /**
     * Checks if the given class is a record.
     * Throws an exception if not.
     */
    public ClassChecker<T, X> isRecord() throws X {
        return checkIs(Class::isRecord, "a record");
    }

    /**
     * Checks if the given class is not a record.
     * Throws an exception if it is.
     */
    public ClassChecker<T, X> isNotRecord() throws X {
        return checkIsNot(Class::isRecord, "a record");
    }

    /**
     * Checks if the given class is an interface.
     * Throws an exception if not.
     */
    public ClassChecker<T, X> isInterface() throws X {
        return checkIs(Class::isInterface, "an interface");
    }

    /**
     * Checks if the given class is not an interface.
     * Throws an exception if it is.
     */
    public ClassChecker<T, X> isNotInterface() throws X {
        return checkIsNot(Class::isInterface, "an interface");
    }

    /**
     * Checks if the given class is abstract.
     * Throws an exception if not.
     */
    public ClassChecker<T, X> isAbstract() throws X {
        return checkHasModifier(Modifier::isAbstract, "abstract");
    }

    /**
     * Checks if the given class is not abstract.
     * Throws an exception if it is.
     */
    public ClassChecker<T, X> isNotAbstract() throws X {
        return checkHasNotModifier(Modifier::isAbstract, "abstract");
    }

    /**
     * Checks if the given class is final.
     * Throws an exception if not.
     */
    public ClassChecker<T, X> isFinal() throws X {
        return checkHasModifier(Modifier::isFinal, "final");
    }

    /**
     * Checks if the given class is final.
     * Throws an exception if it is.
     */
    public ClassChecker<T, X> isNotFinal() throws X {
        return checkHasNotModifier(Modifier::isFinal, "final");
    }

    /**
     * Checks if the given class is public.
     * Throws an exception if not.
     */
    public ClassChecker<T, X> isPublic() throws X {
        return checkHasModifier(Modifier::isPublic, "public");
    }

    /**
     * Checks if the given class is not public.
     * Throws an exception if it is.
     */
    public ClassChecker<T, X> isNotPublic() throws X {
        return checkHasNotModifier(Modifier::isPublic, "public");
    }

    /**
     * Checks if the given class is private.
     * Throws an exception if not.
     */
    public ClassChecker<T, X> isPrivate() throws X {
        return checkHasModifier(Modifier::isPrivate, "private");
    }

    /**
     * Checks if the given class is not private.
     * Throws an exception if it is.
     */
    public ClassChecker<T, X> isNotPrivate() throws X {
        return checkHasNotModifier(Modifier::isPrivate, "private");
    }

    /**
     * Checks if the given class is protected.
     * Throws an exception if not.
     */
    public ClassChecker<T, X> isProtected() throws X {
        return checkHasModifier(Modifier::isProtected, "protected");
    }

    /**
     * Checks if the given class is not protected.
     * Throws an exception if it is.
     */
    public ClassChecker<T, X> isNotProtected() throws X {
        return checkHasNotModifier(Modifier::isProtected, "protected");
    }

    /**
     * Checks if the given class is static.
     * Throws an exception if not.
     */
    public ClassChecker<T, X> isStatic() throws X {
        return checkHasModifier(Modifier::isStatic, "static");
    }

    /**
     * Checks if the given class is not static.
     * Throws an exception if it is.
     */
    public ClassChecker<T, X> isNotStatic() throws X {
        return checkHasNotModifier(Modifier::isStatic, "static");
    }

    /**
     * Checks if the given class is sealed.
     * Throws an exception if not.
     */
    public ClassChecker<T, X> isSealed() throws X {
        return checkIs(Class::isSealed, "sealed");
    }

    /**
     * Checks if the given class is not sealed.
     * Throws an exception if it is.
     */
    public ClassChecker<T, X> isNotSealed() throws X {
        return checkIsNot(Class::isSealed, "sealed");
    }

}