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
        MODIFIES_PRIVATE_FIELD,
        MODIFIES_FINAL_FIELD,
        MODIFIES_IMMUTABLE_COLLECTION,
        INVOKES_PRIVATE_METHOD,
        USES_PRIVATE_CONSTANT,
        MODIFIES_JDK_BEHAVIOUR,
    }

}