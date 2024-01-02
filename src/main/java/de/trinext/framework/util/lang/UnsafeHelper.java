package de.trinext.framework.util.lang;

import de.trinext.framework.util.internal.annotations.EvilShitFuckery;
import sun.misc.Unsafe;

import static de.trinext.framework.util.internal.annotations.EvilShitFuckery.BlackMagic.READS_PRIVATE_VALUE;

/**
 * A helper class for unsafe low-level instructions.
 * <p>
 * Only use this, if you have no other choice.
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "InterfaceNeverImplemented"})
public interface UnsafeHelper {

    /**
     * Returns an instance of {@link Unsafe}.
     *
     * @throws IllegalStateException when breaking eventually.
     */
    @EvilShitFuckery(blackMagic = READS_PRIVATE_VALUE)
    static Unsafe getUnsafe() {
        try {
            var field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalStateException(e);
        }
    }

}