package de.trinext.framework.util.lang;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import de.trinext.framework.util.internal.annotations.EvilShitFuckery;

import static de.trinext.framework.util.internal.annotations.EvilShitFuckery.BlackMagic.IGNORES_CONSTRUCTOR;
import static de.trinext.framework.util.internal.annotations.EvilShitFuckery.BlackMagic.MODIFIES_FINAL_FIELD;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings("unused")
public final class ReflectionHelper {

    private static final Supplier<IllegalArgumentException> NO_FIELD_MAPPER = () -> new IllegalArgumentException("fieldMapper has to be passed even for classes without fields. Use f -> null if necessary.");

    private ReflectionHelper() { throw new AssertionError(); }

    /**
     * Tries to get the actual type arguments of the given {@link Field}.
     *
     * @param field The {@link Field} to get the actual type arguments from.
     *
     * @return An {@link Optional} containing all actual type arguments of the given {@link Field}
     * or {@link Optional#empty()} if the given {@link Field} has not a {@link ParameterizedType}.
     */
    public static Optional<Class<?>[]> tryGetActualTypeArguments(Field field) {
        return field.getGenericType() instanceof ParameterizedType paramType
               ? Optional.of(Arrays.stream(paramType.getActualTypeArguments())
                .map(t -> (Class<?>) t)
                .toArray(Class<?>[]::new))
               : Optional.empty();
    }

    /**
     * Creates an instance of the given {@link Class} without calling any constructor.
     * <p>
     * Note, that this implies that instances produced by this method are not initialized / constructed as intended.
     * Calling {@code createInstanceWithoutConstructor(String.class);} for example,
     * will create an instance of {@link String} without its internal array.
     * This is not equivalent to {@code new String();} or {@code ""}.
     * <p>
     * It's therefore recommended to manually set all fields after calling this method.
     *
     * @param cls a non-abstract class, record or enum.
     */
    @EvilShitFuckery(blackMagic = IGNORES_CONSTRUCTOR)
    @SuppressWarnings("SimplifiableIfStatement")
    public static <T> T createInstanceWithoutConstructor(Class<T> cls) {
        new ClassChecker<>(cls, IllegalArgumentException::new)
                .isNotAbstract().isNotInterface().isNotEnum();
        try {
            if (cls.isArray())
                return (T) Array.newInstance(cls.getComponentType(), 0);
            if (cls.isPrimitive()) // Return default value of wrapper class
                return (T) createInstanceWithoutConstructor(ClassHelper.forceWrapperType(cls));
            return (T) UnsafeHelper.getUnsafe().allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Creates an instance of the passed {@link Class} without calling the constructor.
     * The values for the fields, provided by the given {@link Function} are directly written at their memory-addresses.
     */
    @EvilShitFuckery(blackMagic = MODIFIES_FINAL_FIELD)
    public static <T> T forceConstructClass(Class<T> cls, Function<? super Field, Object> fieldMapper) {
        new ClassChecker<>(cls, IllegalArgumentException::new)
                .isNotEnum().isNotArray().isNotAbstract().isNotInterface().isNotPrimitive().isNotRecord();
        if (fieldMapper == null)
            throw NO_FIELD_MAPPER.get();
        var unsafe = UnsafeHelper.getUnsafe();
        var instance = createInstanceWithoutConstructor(cls);
        for (var field : cls.getDeclaredFields())
            unsafe.putObject(instance, unsafe.objectFieldOffset(field), fieldMapper.apply(field));
        return instance;
    }

    /**
     * Creates an instance of a {@link Record} by calling its constructor with the values, provided by the given {@link Function}.
     *
     * @param cls The {@link Record} class to instantiate.
     * @param fieldMapper A {@link Function} that maps a {@link RecordComponent}s to their values.
     *
     * @throws IllegalAccessException if the constructor is inaccessible.
     * @throws InvocationTargetException if the constructor throws an exception.
     */
    public static <T> T constructRecord(Class<T> cls, Function<? super RecordComponent, Object> fieldMapper) throws IllegalAccessException, InvocationTargetException {
        new ClassChecker<>(cls, IllegalArgumentException::new)
                .isRecord();
        if (fieldMapper == null)
            throw NO_FIELD_MAPPER.get();
        try {
            var constructor = cls.getDeclaredConstructor(Arrays.stream(cls.getRecordComponents())
                    .map(RecordComponent::getType)
                    .toArray(Class<?>[]::new));
            constructor.setAccessible(true);
            return constructor.newInstance(Arrays.stream(cls.getRecordComponents())
                    .map(fieldMapper)
                    .toArray());
        } catch (InstantiationException | NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

}