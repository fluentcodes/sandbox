package fluentcodes.sandbox;

import org.junit.Assert;
import org.junit.Test;

public class MethodRefTest {

    @FunctionalInterface
    private interface Block { void apply(); }

    @FunctionalInterface
    private interface ParamBlock<T> { void apply(T value); }

    @FunctionalInterface
    private interface ParamBlockWithResult<T> { T apply(T value); }

    @Test
    public void testInstanceMethodRef() throws Exception {
        final PairOfInts pair = PairOfInts.valueOf(1, 2);

        final Block swapper = pair::swap;
        swapper.apply();    // calls pair.swap()

        Assert.assertEquals(2, pair.getFirst());
        Assert.assertEquals(1, pair.getSecond());

        final ParamBlock<PairOfInts> swapper2 = PairOfInts::swap;
        swapper2.apply(pair);   // calls pair.swap() too

        Assert.assertEquals(1, pair.getFirst());
        Assert.assertEquals(2, pair.getSecond());
    }

    @Test
    public void testStaticMethodRef() throws Exception {
        final PairOfInts pair = PairOfInts.valueOf(1, 2);

        final ParamBlock<PairOfInts> staticSwapper = PairOfInts::swapIt;
        staticSwapper.apply(pair);  // calls PairOf.swapIt(pair)

        Assert.assertEquals(2, pair.getFirst());
        Assert.assertEquals(1, pair.getSecond());

        final ParamBlockWithResult<PairOfInts> staticSwapper2 = PairOfInts::swapped;
        final PairOfInts swappedPair = staticSwapper2.apply(pair);

        Assert.assertEquals(2, pair.getFirst());
        Assert.assertEquals(1, pair.getSecond());

        Assert.assertEquals(1, swappedPair.getFirst());
        Assert.assertEquals(2, swappedPair.getSecond());
    }

    @Test
    public void testPair() throws Exception {
        final PairOfInts pair = PairOfInts.valueOf(1, 2);
        Assert.assertEquals(1, pair.getFirst());
        Assert.assertEquals(2, pair.getSecond());

        pair.swap();
        Assert.assertEquals(2, pair.getFirst());
        Assert.assertEquals(1, pair.getSecond());

        pair.swap();
        Assert.assertEquals(1, pair.getFirst());
        Assert.assertEquals(2, pair.getSecond());
    }

    private static class PairOfInts {
        private int first;
        private int second;

        public static PairOfInts valueOf(final int first, final int second) {
            return new PairOfInts(first, second);
        }

        public static PairOfInts swapped(final PairOfInts source) {
            return new PairOfInts(source.getSecond(), source.getFirst());
        }

        private PairOfInts(final int first, final int second) {
            this.first = first;
            this.second = second;
        }

        public int getFirst() {
            return first;
        }

        public int getSecond() {
            return second;
        }

        public void swap() {
            final int temp = first;
            first = second;
            second = temp;
        }

        public static void swapIt(final PairOfInts pair) {
            pair.swap();
        }
    }

}
