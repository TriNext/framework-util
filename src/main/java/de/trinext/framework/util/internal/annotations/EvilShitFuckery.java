package de.trinext.framework.util.internal.annotations;

import java.lang.annotation.*;

/**
 * Functions that use this annotation are highly unstable
 * and plainly ignore what you think you know about Java.
 * <p>
 * Comment these functions with a detailed explanation of what they do.
 *
 * @author Dennis Woithe
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@SuppressWarnings("unused")
public @interface EvilShitFuckery {

    /** Description of the unexpected behaviour. */
    BlackMagic[] blackMagic();

    enum BlackMagic {

        /**
         * Creates an instance of a class without calling the constructor.
         * This can lead to instances being in an illegal, uninitialized state
         * or instances for classes that should not be constructed at all. (private constructor)
         */
        IGNORES_CONSTRUCTOR,

        /**
         * Changes the content of a field that's private without calling a setter or
         * any other method on the class.
         */
        MODIFIES_PRIVATE_FIELD,

        /**
         * Modifies a final field or constant.
         */
        MODIFIES_FINAL_FIELD,

        /**
         * Changes the contents of instances of
         * {@link java.util.Collections.UnmodifiableCollection},
         * {@link java.util.Collections.UnmodifiableMap},
         * {@link java.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry}
         * or other datastructures that ought to be unmodifiable.
         */
        MODIFIES_IMMUTABLE_STRUCT,

        /**
         * Invokes a method, static function or constructor that's marked as {@code private}.
         */
        INVOKES_PRIVATE_FUNC,

        /**
         * Reads a value of a static or non-static field that's marked as {@code private}.
         */
        READS_PRIVATE_VALUE,

        /**
         * Changes settings of the JVM at runtime that are expected to be immutable.
         * This can affect for example environment variables, the default charset, timezone, garbage collector, etc.
         */
        MODIFIES_JVM_BEHAVIOUR,
    }

}