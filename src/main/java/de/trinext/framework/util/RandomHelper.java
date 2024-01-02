package de.trinext.framework.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;
import java.util.stream.*;

/**
 * A helper that generates random values for tests.
 * As the implementation currently uses {@link ThreadLocalRandom} it is not suitable for cryptographic purposes.
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess", "HardCodedStringLiteral", "ConstantExpression", "MagicCharacter", "BoundedWildcard"})
public final class RandomHelper {

    private RandomHelper() {
        throw new AssertionError();
    }

    @SuppressWarnings("SharedThreadLocalRandom")
    private static final ThreadLocalRandom THREAD_LOCAL_RANDOM = ThreadLocalRandom.current();

    // ==== STATIC FUNCTIONS ================================================= //

    /** Generates n random bytes */
    public static Stream<Byte> randomBytes(long amount) {
        return randomInts(amount).mapToObj(i -> ((Integer) i).byteValue());
    }

    /** Generates n random shorts */
    public static Stream<Short> randomShorts(long amount) {
        return randomInts(amount).mapToObj(i -> ((Integer) i).shortValue());
    }

    /** Generates n random ints */
    public static IntStream randomInts(long amount) {
        return THREAD_LOCAL_RANDOM.ints(amount);
    }

    /** Generates n random doubles */
    public static DoubleStream randomDoubles(long amount) {
        return THREAD_LOCAL_RANDOM.doubles(amount);
    }

    /** Generates n random floats */
    public static Stream<Float> randomFloats(long amount) {
        return randomDoubles(amount).mapToObj(d -> ((Double) d).floatValue());
    }

    /** Generates n random longs */
    public static LongStream randomLongs(long amount) {
        return THREAD_LOCAL_RANDOM.longs(amount);
    }

    /** Generates n random {@link BigDecimal}s. */
    public static Stream<BigDecimal> randomBigDecs(long amount) {
        return LongStream.range(0, amount).mapToObj(ignored -> randomBigDec());
    }

    /** Generates n random {@link String}s with the passed max length. */
    public static Stream<String> randomStrings(int maxWordLength, long amount) {
        return THREAD_LOCAL_RANDOM
                .ints(amount, 0, maxWordLength + 1)
                .mapToObj(byte[]::new)
                .peek(THREAD_LOCAL_RANDOM::nextBytes)
                .map(String::new);
    }

    /** Executes the consumer for the passed amount of random floats. */
    public static void runForRandomFloats(long amount, Consumer<Float> consumer) {
        randomFloats(amount).forEach(consumer);
    }

    /** Executes the consumer for the passed amount of random doubles. */
    public static void runForRandomDoubles(long amount, DoubleConsumer consumer) {
        randomDoubles(amount).forEach(consumer);
    }

    /** Executes the consumer for the passed amount of random {@link BigInteger}s. */
    public static void runForRandomBigInts(long amount, Consumer<BigInteger> consumer) {
        randomInts(amount).mapToObj(BigInteger::valueOf).forEach(consumer);
    }

    /** Executes the consumer for the passed amount of random bytes. */
    public static void runForRandomBytes(long amount, Consumer<Byte> consumer) {
        randomBytes(amount).forEach(consumer);
    }

    /** Executes the consumer for the passed amount of random shorts. */
    public static void runForRandomShorts(long amount, Consumer<Short> consumer) {
        randomShorts(amount).forEach(consumer);
    }

    /** Executes the consumer for the passed amount of random ints. */
    public static void runForRandomInts(long amount, IntConsumer consumer) {
        randomInts(amount).forEach(consumer);
    }

    /** Executes the consumer for the passed amount of random longs. */
    public static void runForRandomLongs(long amount, LongConsumer consumer) {
        randomLongs(amount).forEach(consumer);
    }

    /** Executes the consumer for the passed amount of random {@link BigDecimal}s. */
    public static void runForRandomBigDecs(long amount, Consumer<BigDecimal> consumer) {
        randomBigDecs(amount).forEach(consumer);
    }

    /**
     * Executes the test for the passed amount of random {@link String}s.
     *
     * @param maxWordLength The max length of each {@link String}
     */
    public static void runForRandomStrings(int maxWordLength, long wordAmount, Consumer<String> test) {
        randomStrings(maxWordLength, wordAmount).forEach(test);
    }

    /** Generates one random byte */
    public static byte randomByte() {
        //noinspection NumericCastThatLosesPrecision
        return (byte) randomInt();
    }

    /** Generates one random short */
    public static short randomShort() {
        //noinspection NumericCastThatLosesPrecision
        return (short) randomInt();
    }

    /** Generates one random int */
    public static int randomInt() {
        return THREAD_LOCAL_RANDOM.nextInt();
    }

    /** Generates one random int between 0 (inclusive) and max (exclusive) */
    public static int randomInt(int max) {
        return THREAD_LOCAL_RANDOM.nextInt(max);
    }

    /** Generates one random int between min (inclusive) and max (exclusive) */
    public static int randomInt(int min, int max) {
        return THREAD_LOCAL_RANDOM.nextInt(min, max);
    }

    /** Generates one random long */
    public static long randomLong() {
        return THREAD_LOCAL_RANDOM.nextLong();
    }

    /** Generates one random float */
    public static float randomFloat() {
        return THREAD_LOCAL_RANDOM.nextFloat();
    }

    /** Generates one random double */
    public static double randomDouble() {
        return THREAD_LOCAL_RANDOM.nextDouble();
    }

    /** Generates one random {@link BigDecimal}. */
    public static BigDecimal randomBigDec() {
        return new BigDecimal(randomLong()).multiply(BigDecimal.valueOf(randomDouble()));
    }

    /** Generates one random {@link BigDecimal} bigger or equals to specified minimum. */
    public static BigDecimal randomBigDecBiggerThan(BigDecimal min) {
        return min.add(randomBigDec().abs());
    }

    /** Generates one random {@link BigDecimal} smaller or equals to specified maximum. */
    public static BigDecimal randomBigDecSmallerThan(BigDecimal max) {
        return max.subtract(randomBigDec().abs());
    }

    /** Generates one random boolean */
    public static boolean randomBool() {
        return THREAD_LOCAL_RANDOM.nextBoolean();
    }

    /** Generates one random character */
    public static char randomChar() {
        return (char) randomShort();
    }

    /** Generates one random {@link String} with the passed max length. */
    public static String randomString(int maxWordLength, Charset charset) {
        var bytes = new byte[maxWordLength];
        THREAD_LOCAL_RANDOM.nextBytes(bytes);
        return new String(bytes, charset);
    }

    /** Generates one random {@link String} with the passed max length. */
    public static String randomString(int maxWordLength) {
        return randomString(maxWordLength, StandardCharsets.UTF_8);
    }

    /** Creates a random {@link String} with the passed max length that matches [A-Za-z]. */
    public static String randomWord(int maxWordLength) {
        var chars = new char[maxWordLength];
        for (var i = 0; i < maxWordLength; i++)
            chars[i] = randomLetter();
        return new String(chars);
    }

    /** Creates a random char that matches [A-Za-z]. */
    @SuppressWarnings({"NumericCastThatLosesPrecision", "ConstantExpression", "HardCodedStringLiteral"})
    public static char randomLetter() {
        return (char) (randomBool()
                       ? THREAD_LOCAL_RANDOM.nextInt('a', 'z' + 1)
                       : THREAD_LOCAL_RANDOM.nextInt('A', 'Z' + 1)
        );
    }

}