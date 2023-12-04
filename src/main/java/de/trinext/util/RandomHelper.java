package de.trinext.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;
import java.util.stream.*;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess", "HardCodedStringLiteral", "ConstantExpression", "MagicCharacter"})
public final class RandomHelper {

    private RandomHelper() {
        throw new AssertionError();
    }

    @SuppressWarnings("SharedThreadLocalRandom")
    private static final ThreadLocalRandom THREAD_LOCAL_RANDOM = ThreadLocalRandom.current();

    // ==== STATIC FUNCTIONS ================================================= //

    /** Generates n random ints */
    public static IntStream randomInts(long amount) {
        return THREAD_LOCAL_RANDOM.ints(amount);
    }

    /** Generates n random doubles */
    public static DoubleStream randomDoubles(long amount) {
        return THREAD_LOCAL_RANDOM.doubles(amount);
    }

    /** Generates n random longs */
    public static LongStream randomLongs(long amount) {
        return THREAD_LOCAL_RANDOM.longs(amount);
    }

    /** Generates n random {@link String}s with the passed max length. */
    public static Stream<String> randomStrings(int maxWordLength, long amount) {
        return THREAD_LOCAL_RANDOM
                .ints(amount, 0, maxWordLength + 1)
                .mapToObj(byte[]::new)
                .peek(THREAD_LOCAL_RANDOM::nextBytes)
                .map(String::new);
    }

    /** Executes the test for the passed amount of random doubles. */
    public static void runForRandomDoubles(long wordAmount, DoubleConsumer consumer) {
        randomDoubles(wordAmount).forEach(consumer);
    }

    /** Executes the test for the passed amount of random {@link Double}s. */
    public static void runForRandomBoxedDoubles(long wordAmount, Consumer<Double> test) {
        randomDoubles(wordAmount).boxed().forEach(test);
    }

    /** Executes the test for the passed amount of random {@link BigInteger}s. */
    public static void runForRandomBigInts(long wordAmount, Consumer<BigInteger> test) {
        randomInts(wordAmount).mapToObj(BigInteger::valueOf).forEach(test);
    }

    /** Executes the test for the passed amount of random ints. */
    public static void runForRandomInts(long wordAmount, IntConsumer test) {
        randomInts(wordAmount).forEach(test);
    }

    /** Executes the test for the passed amount of random {@link Integer}s. */
    public static void runForRandomBoxedInts(long wordAmount, Consumer<Integer> test) {
        randomInts(wordAmount).boxed().forEach(test);
    }

    /** Executes the test for the passed amount of random longs. */
    public static void runForRandomLongs(long wordAmount, LongConsumer test) {
        randomLongs(wordAmount).forEach(test);
    }

    /** Executes the test for the passed amount of random {@link Long}s. */
    public static void runForRandomBoxedLongs(long wordAmount, Consumer<Long> test) {
        randomLongs(wordAmount).boxed().forEach(test);
    }

    /** Executes the test for the passed amount of random {@link BigDecimal}s. */
    public static void runForRandomBigDecs(long wordAmount, Consumer<BigDecimal> test) {
        randomDoubles(wordAmount).mapToObj(BigDecimal::valueOf).forEach(test);
    }

    /**
     * Executes the test for the passed amount of random {@link String}s.
     *
     * @param maxWordLength The max length of each {@link String}
     */
    public static void runForRandomStrings(int maxWordLength, long wordAmount, Consumer<String> test) {
        randomStrings(maxWordLength, wordAmount).forEach(test);
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

    /** Generates one random double */
    public static double randomDouble() {
        return THREAD_LOCAL_RANDOM.nextDouble();
    }

    /** Generates one random boolean */
    public static boolean randomBool() {
        return THREAD_LOCAL_RANDOM.nextBoolean();
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

    @SuppressWarnings("NumericCastThatLosesPrecision")
    public static char randomLetter() {
        var tlr = THREAD_LOCAL_RANDOM;
        return (char) (randomBool()
                       ? tlr.nextInt('a', 'z' + 1)
                       : tlr.nextInt('A', 'Z' + 1)
        );
    }

}