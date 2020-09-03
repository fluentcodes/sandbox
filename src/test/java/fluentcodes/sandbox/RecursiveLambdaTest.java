package fluentcodes.sandbox;

import org.junit.Assert;
import org.junit.Test;

public class RecursiveLambdaTest {

    @FunctionalInterface
    private interface IntFunc {
        int calc(int i);
    }

// doesn't work any more; self-reference has been removed from the JSR (see http://mail.openjdk.java.net/pipermail/lambda-dev/2012-October/006216.html)
//    private final IntFunc factorial = i -> i <= 1 ? 1 : i * factorial.calc(i - 1);

    @Test
    public void testRecursive() throws Exception {
// but you can do this:
        final IntFunc[] applier = { null };
        final IntFunc factorial = applier[0] = i -> (i <= 1) ? 1 : i * applier[0].calc(i - 1);

        Assert.assertEquals(1, factorial.calc(0));
        Assert.assertEquals(1, factorial.calc(1));
        Assert.assertEquals(1 * 2, factorial.calc(2));
        Assert.assertEquals(1 * 2 * 3 * 4 * 5 * 6, factorial.calc(6));
        Assert.assertEquals(1 * 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9 * 10 * 11 * 12, factorial.calc(12));
    }

    @Test
    public void testRecursiveLocal() throws Exception {
        // recursive not allowed for local variable (java: variable factorial2 might not have been initialized)
//        final IntFunc factorial2 = i -> i <= 1 ? 1 : i * factorial2.calc(i - 1);

        // and this wouldn't work either
        // (java: local variables referenced from a lambda expression must be final or effectively final)
//        IntFunc factorial3 = null;
//        factorial3 = i -> i <= 1 ? 1 : i * factorial3.calc(i - 1);    // recursive definition wouldn't work here
    }

}
