package fluentcodes.sandbox;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Consumer;

public class LambdaTest {

    private static final fluentcodes.sandbox.SimpleLogger logger = System.out::println;

    @Test
    public void testComparator() throws Exception {
        final String[] numbers = { "0.23e2", "873.7", "-312.1", "43.3", "653", "-12", "734e-1" };

        logger.log("Unsorted: " + Arrays.toString(numbers));
        Arrays.sort(numbers, (s1, s2) -> Double.valueOf(s1).compareTo(Double.valueOf(s2)));
        logger.log("Sorted:   " + Arrays.toString(numbers));
    }

    @Test
    public void testRunnable() throws Exception {
        logger.log("Run before thread; thread.id: " + Thread.currentThread().getId());

        final Thread oldschool = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.log("Run on thread; thread.id: " + Thread.currentThread().getId());
            }
        });

        final Thread thread = new Thread(() -> logger.log("Run on thread; thread.id: " + Thread.currentThread().getId()));
        thread.start();
        thread.join();

        logger.log("Run after thread; thread.id: " + Thread.currentThread().getId());
    }

    @Test
    public void testScope() throws Exception {

        final int i1 = 10;
        final int i2 = 20;
        int i3 = 30;

        final Consumer<String> consumer1 = s -> logger.log(s + i1); // i1 is final
        final Consumer<String> consumer2 = s -> logger.log(s + i2); // i2 is 'effectively final'
//        final Consumer<String> consumer3 = s -> logger.log(s + i3); // java: local variables referenced from a lambda expression must be final or effectively final
//        final Consumer<String> consumer4 = s -> { int i1 = 1; logger.log(s); }; // java: variable i1 is already defined in method testScope()

        ++i3;
    }

    @Test
    public void testBinding() throws Exception {
        final ScopeTest instance = new ScopeTest();
        Assert.assertEquals(1, instance.getInstanceVar1());
        Assert.assertEquals(2, instance.getInstanceVar2());
        instance.consumer1.accept("i1: ");
        instance.consumer2.accept("i2: ");
        instance.consumer3.accept("c3: ");
        instance.consumer4.accept(2);
        Assert.assertEquals(4, instance.getInstanceVar2());
        instance.setInstanceVar2(20);
        instance.consumer2.accept("i2: ");
    }


    private static class ScopeTest {
        private final int i1 = 1;
        private int i2 = 2;

        public Consumer<String> consumer1 = s -> logger.log(s + i1);
        public Consumer<String> consumer2 = s -> logger.log(s + i2);
        public Consumer<String> consumer3 = s -> { int i1 = 4; logger.log(s + i1); };  // local i1 shadows the instance var
        public Consumer<Integer> consumer4 = i -> setInstanceVar2(i * i2);

        public int getInstanceVar1() { return i1; }

        public int getInstanceVar2() { return i2; }
        public void setInstanceVar2(final int i2) { this.i2 = i2; }
    }



}
