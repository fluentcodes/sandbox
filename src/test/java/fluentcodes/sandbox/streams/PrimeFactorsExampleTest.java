package fluentcodes.sandbox.streams;

import fluentcodes.sandbox.SimpleLogger;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.LongSupplier;
import java.util.stream.*;

public class PrimeFactorsExampleTest {

    private static final fluentcodes.sandbox.SimpleLogger logger = System.out::println;
    private static final fluentcodes.sandbox.SimpleLogger peekLogger = System.out::println;
//    private static final SimpleLogger peekLogger = s -> {};

    @Test
    public void testPrimeFactors() throws Exception {
        Assert.assertArrayEquals(new long[]{-1, 3, 5}, PrimeFactors.factors(-15).toArray());
        Assert.assertArrayEquals(new long[]{-1, 2}, PrimeFactors.factors(-2).toArray());
        Assert.assertArrayEquals(new long[]{0}, PrimeFactors.factors(0).toArray());
        Assert.assertArrayEquals(new long[]{1}, PrimeFactors.factors(1).toArray());
        Assert.assertArrayEquals(new long[]{2}, PrimeFactors.factors(2).toArray());
        Assert.assertArrayEquals(new long[]{3}, PrimeFactors.factors(3).toArray());
        Assert.assertArrayEquals(new long[]{2, 2}, PrimeFactors.factors(4).toArray());
        Assert.assertArrayEquals(new long[]{2, 13}, PrimeFactors.factors(26).toArray());
        Assert.assertArrayEquals(new long[]{2, 2, 2, 2, 3}, PrimeFactors.factors(48).toArray());
        Assert.assertArrayEquals(new long[]{2, 3, 5, 7, 11, 13}, PrimeFactors.factors(30030).toArray());
        Assert.assertArrayEquals(new long[]{2, 3, 5, 7, 11, 13, 17}, PrimeFactors.factors(510510).toArray());
        Assert.assertArrayEquals(new long[]{2, 3, 5, 7, 11, 13, 17, 19}, PrimeFactors.factors(9699690).toArray());

        Assert.assertArrayEquals(new long[]{9973}, PrimeFactors.factors(9973).toArray());    // 9973 is prime
//        Assert.assertArrayEquals(new long[]{49957}, PrimeFactors.factors(49957).toArray());    // 49957 is prime; might cause stackoverflow, because java does not support tail recursion optimization
    }

    @Test
    public void testFlatMap() throws Exception {
        final List<Long> list = new Random().ints().filter(i -> Math.abs(i) < 1000).limit(5).mapToObj(Long::valueOf).collect(ArrayList<Long>::new, List::add, List::addAll);

        logger.log(list.toString());

        logger.log(
            list.stream()
                .map(PrimeFactors::factorList)
                .collect(Collectors.toList()).toString());

//        logger.log(
//            list.stream()
//                .map(i -> {
//                    final List<Long> result = new ArrayList<>();
//                    final List<Long> fac = PrimeFactors.factorList(i);
//                    result.add(i.longValue());      // the actual value
//                    result.addAll(fac);             // the factors
//                    return result;
//                })
//                .collect(Collectors.toList()).toString());


        logger.log (
            list.stream()
                .flatMap(PrimeFactors::factorsOfLong)
                .collect(Collectors.toList()).toString());

//        logger.log(
//            list.stream()
//                .flatMapToLong(PrimeFactors::factors)
//                .<List<Long>>collect(ArrayList::new, List::add, List::addAll).toString());

//        logger.log(
//            list.stream()
//                .flatMap(i -> {
//                    final List<Long> result = new ArrayList<>();
//                    final List<Long> fac = PrimeFactors.factorList(i);
//                    result.add(i.longValue());      // the actual value
//                    result.add((long)fac.size());   // the number of factors
//                    result.addAll(fac);             // the factors
//                    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(result.iterator(), Spliterator.ORDERED), false);
//                })
//                .collect(Collectors.toList()).toString());
    }

    @Test
    public void testInfiniteStream() throws Exception {
        logger.log(Arrays.toString(fibonacci().limit(10).toArray()));
        fibonacci().limit(10).sorted().toArray();
//        fibonacci().sorted().limit(10).toArray(); // might run out of heapspace:  ;-)
    }

    private static LongStream fibonacci() {
        final long[] pair = {0, 1};
        final LongSupplier f = () -> {
            final long r = pair[0];
            final long n = pair[1];
            pair[0] = pair[1];
            pair[1] = r + n; /*if (r < 0) throw new IllegalStateException("Long value exceeded.");*/
            return r;
        };
        return LongStream.generate(f);
    }

    @Test
    public void testIntRangeStream() throws Exception {

        final int maxTests = 200000;

        logger.log("\nResult: "
            + IntStream.range(0, maxTests)
            .mapToObj(i -> UUID.randomUUID().toString())
            .filter(s -> s.contains("affe"))
            .findFirst()
            .orElse("No match")
            + " found.");
    }

    @Test
    public void testCollectionStream() throws Exception {

        final int maxNumbers = 10000;
        final List<String> uuids = getRandomUids(maxNumbers);
        final int[] cnt = {0};

        logger.log("\nQuery result: "
            + uuids.stream()
            .peek(s -> {
                ++cnt[0];
                peekLogger.log("\npeek1: " + s);
            })
            .filter(s -> s.contains("a"))
            .peek(s -> peekLogger.log("peek2: " + s))
            .filter(s -> s.contains("af"))
            .peek(s -> peekLogger.log("peek3: " + s))
            .filter(s -> s.contains("aff"))
            .peek(s -> peekLogger.log("peek4: " + s))
            .filter(s -> s.contains("affe"))
            .findFirst()
            .orElse("No match")
            + " after " + cnt[0] + " tests.");

        Assert.assertTrue(cnt[0] == maxNumbers || uuids.get(cnt[0] - 1).contains("affe"));
    }

    private List<String> getRandomUids(final int count) {
        return Arrays.asList(IntStream.range(0, count).mapToObj(i -> UUID.randomUUID()).map(UUID::toString).toArray(String[]::new));
    }

    private static class PrimeFactors implements Iterable<Long> {

        private long initialValue;

        public static LongStream factors(final long value) {
            return StreamSupport.longStream(Spliterators.spliteratorUnknownSize(new PrimeFactors(value).iterator(), Spliterator.ORDERED), false);
        }

        public static Stream<Long> factorsOfLong(final long value) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new PrimeFactors(value).iterator(), Spliterator.ORDERED), false);
        }

        public static List<Long> factorList(final long value) {
            return Arrays.asList(factors(value).mapToObj(Long::valueOf).toArray(Long[]::new));
        }

        private PrimeFactors(final long initialValue) {
            this.initialValue = initialValue;
        }

        @Override
        public PrimitiveIterator.OfLong iterator() {
            return new Iterator(initialValue);
        }

        private class Iterator implements PrimitiveIterator.OfLong {
            private long currentValue;
            private long currentFactor;
            private boolean done;

            private Iterator(final long initialValue) {
                this.currentValue = initialValue;
                this.currentFactor = 2;
                this.done = false;
            }

            @Override
            public long nextLong() {
                return calcNext(currentValue, currentFactor);
            }

            @Override
            public boolean hasNext() {
                return !done;
            }

            private void update(final long value, final long factor) {
                this.currentValue = value;
                this.currentFactor = factor;
            }

            private void done() {
                this.done = true;
            }

            private long calcNext(final long value, final long factor) {
                if (value < 0) {
                    update(Math.abs(value), factor);
                    return -1;
                } else if (value <= 1) {
                    done();
                    return value;
                } else if (0 == value % factor) {
                    final long next = value / factor;
                    if (next < factor) {
                        done();
                    } else {
                        update(next, factor);
                    }
                    return factor;
                } else if (factor <= value / 2) {
                    return calcNext(value, factor + 1);
                } else {
                    done();
                    return value;
                }
            }
        }
    }

}
