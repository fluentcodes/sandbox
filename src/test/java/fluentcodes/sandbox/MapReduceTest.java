package fluentcodes.sandbox;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntSupplier;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MapReduceTest {

    private static final fluentcodes.sandbox.SimpleLogger logger = System.out::println;
    public static final List<String> lines = new ArrayList<>();
    public static int expectedCount;

    @BeforeClass
    public static void setupClass() throws Exception {
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(fluentcodes.sandbox.MapReduceTest.class.getClassLoader().getResourceAsStream("RELEASE-NOTES")))) {
            String line;
            while (null != (line = br.readLine())) {
                lines.add(line);
                expectedCount += line.length();
            }
        }
        for (int i = 0; i < 10; ++i) {
            lines.addAll(lines);
            expectedCount *= 2;
        }
        logger.log("expected: " + expectedCount);
    }

    @Test
    public void test1SimpleCount() throws Exception {
        final int count = logTimes("simpleCount", () -> {
            final int[] cnt = { 0 };
            lines.forEach(s -> { cnt[0] += s.length(); } );
            return cnt[0];
        });
        Assert.assertEquals(expectedCount, count);
    }

    @Test
    public void test2MapReduceCount() throws Exception {
        final int count = logTimes("mapReduceCount", () ->
                lines.stream().mapToInt((String::length)).reduce(0, (i1, i2) -> i1 + i2)
        );
        Assert.assertEquals(expectedCount, count);
    }

    @Test
    public void test3ParallelCount() throws Exception {
        final int count = logTimes("parallelCount", () ->
                lines.parallelStream().mapToInt((String::length)).reduce(0, (i1, i2) -> i1 + i2)
        );
        Assert.assertEquals(expectedCount, count);
    }

    private int logTimes(final String testName, final IntSupplier counter) {
        final long start = System.currentTimeMillis();
        final int result = counter.getAsInt();
        final long duration = System.currentTimeMillis() - start;
        logger.log("Duration for " + testName + ": " + duration);
        return result;
    }


}
