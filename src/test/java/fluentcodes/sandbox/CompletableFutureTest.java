package fluentcodes.sandbox;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.*;

public class CompletableFutureTest {
    private final String remote = "http://www.ecube.de";

    private ExecutorService executor;

    @Before
    public void setup() throws Exception {
        System.out.println("Testing thread: " + Thread.currentThread().getId());
        executor = Executors.newFixedThreadPool(4);
    }

    @After
    public void teardown() throws Exception {
        executor.shutdown();
    }


    @Test
    public void testSimpleFuture() {
        try {
            final Future<String> response = executor.submit(() -> httpGet(remote));

            // do something else
            Thread.sleep(800);

            final Instant start = Instant.now();
            final String body = response.get();  // response.get() is blocking
//            final String body = response.get(200L, TimeUnit.MILLISECONDS);
            printWithElapsedTime(start, body);
        } catch (final Exception e) {
            System.err.println(e.toString());
        }
    }

    @Test
    public void testCompletableFutureBlocking() throws Exception {
//        CompletableFuture<String> response = CompletableFuture.supplyAsync(() -> httpGet(remote));  // ForkJoinPool.commonPool()
        final CompletableFuture<String> response = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ba";
        }, executor);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final Instant start = Instant.now();
        final String body = response.get();
        printWithElapsedTime(start, body);
    }

    @Test
    public void testCompletableFutureWithTimeout() throws Exception {
        final CompletableFuture<String> response = CompletableFuture.supplyAsync(() -> httpGet(remote), executor);
        try {
            final Instant start = Instant.now();
            final String body = response.get(200L, TimeUnit.MILLISECONDS);
            printWithElapsedTime(start, body);
        } catch (final TimeoutException e) {
            System.err.println("Timeout!");
        }
    }

    @Test
    public void testCompletableFutureFallback() throws Exception {
        final CompletableFuture<String> response = CompletableFuture.supplyAsync(() -> httpGet(remote), executor);
        Thread.sleep(100L);
        final Instant start = Instant.now();
        final String body = response.getNow("Request timed out");
        printWithElapsedTime(start, body);
    }

    @Test(expected = ExecutionException.class)
    public void testFutureExceptionUncaught() throws Exception {
        final CompletableFuture<String> response = CompletableFuture.supplyAsync(() -> httpGet("x" + remote), executor);
        final Instant start = Instant.now();
        final String body = response.get();
        printWithElapsedTime(start, body);
    }

    @Test
    public void testFutureException() throws Exception {
        final CompletableFuture<String> response = CompletableFuture
                .supplyAsync(() -> httpGet("x" + remote), executor)
                .exceptionally(ex -> "async exception caught " + ex.getMessage() + ": " + ex.getCause().getMessage());
        final Instant start = Instant.now();
        final String body = response.get();
        printWithElapsedTime(start, body);
    }

    @Test
    public void testWhenComplete() throws Exception {
        final CountDownLatch doneSignal = new CountDownLatch(1);
        final Instant start = Instant.now();
        CompletableFuture
                .supplyAsync(() -> httpGet(remote), executor)
//                .exceptionally(ex -> "async exception caught " + ex.getMessage() + ": " + ex.getCause().getMessage())
                .whenComplete((body, ex) -> {
                    if (ex != null) {
                        System.err.println("completed with exception " + ex.getMessage());
                    } else {
                        printWithElapsedTime(start, body);
                    }
                    doneSignal.countDown();
                });

        doneSignal.await();
    }


    @Test
    public void testCompletableFutureWithAcceptConsumer() throws Exception {
        final CountDownLatch doneSignal = new CountDownLatch(1);
        final Instant start = Instant.now();
        CompletableFuture
                .supplyAsync(() -> httpGet(remote), executor)
                .exceptionally(ex -> "async exception caught " + ex.getMessage() + ": " + ex.getCause().getMessage())
                .thenAccept(body -> {
                    printWithElapsedTime(start, body);
                })
                .whenComplete((r, e) -> doneSignal.countDown());

        doneSignal.await();
    }

    @Test
    public void testCompletableFutureWithAsyncAcceptConsumer() throws Exception {
        final CountDownLatch doneSignal = new CountDownLatch(1);
        final Instant start = Instant.now();
        CompletableFuture
                .supplyAsync(() -> httpGet(remote), executor)
                .thenAcceptAsync(body -> {
                    printWithElapsedTime(start, body);
                })
                .whenComplete((r, e) -> doneSignal.countDown());

        doneSignal.await();
    }

    @Test
    public void testCompletableFutureJoined() throws Exception {
        final CountDownLatch doneSignal = new CountDownLatch(1);
        final Instant start = Instant.now();
        CompletableFuture
                .supplyAsync(() -> httpGet(remote), executor)
                .thenApply(this::extractHead)
                .thenAccept(head -> {
                    printWithElapsedTime(start, head);
                })
                .whenComplete((r, e) -> doneSignal.countDown());

        doneSignal.await();
    }

    @Test
    public void testCompletableFutureAsyncJoined() throws Exception {
        final CountDownLatch doneSignal = new CountDownLatch(1);
        final Instant start = Instant.now();
        CompletableFuture
                .supplyAsync(() -> httpGet(remote), executor)
                .thenApplyAsync(this::extractHead)
                .thenAcceptAsync(head -> {
                    printWithElapsedTime(start, head);
                })
                .whenComplete((r, e) -> doneSignal.countDown());


        doneSignal.await();
    }

    private CompletableFuture<String> httpGetFuture(final String remote) {
        return CompletableFuture.supplyAsync(() -> httpGet(remote), executor);
    }

    private CompletableFuture<String> extractHeadFuture(final String doc) {
        return CompletableFuture.supplyAsync(() -> extractHead(doc), executor);
    }

    @Test
    public void testCompletableFutureCompose() throws Exception {
        final CountDownLatch doneSignal = new CountDownLatch(1);
        final Instant start = Instant.now();
        httpGetFuture(remote)
                .thenCompose(this::extractHeadFuture)
                .thenAcceptAsync(head -> {
                    printWithElapsedTime(start, head);
                })
                .whenComplete((r, e) -> doneSignal.countDown());

        doneSignal.await();
    }

    @Test
    public void testCompletableFutureAcceptBoth() throws Exception {
        final CountDownLatch doneSignal = new CountDownLatch(1);
        final Instant start = Instant.now();
        final CompletableFuture<String> remote1 = httpGetFuture(remote).thenCompose(this::extractHeadFuture);
        final CompletableFuture<String> remote2 = httpGetFuture("http://google.com").thenCompose(this::extractHeadFuture);
        remote1.thenAcceptBothAsync(remote2, (h1, h2) -> {
            printWithElapsedTime(start, h1.concat(h2));
        })
                .whenComplete((r, e) -> doneSignal.countDown());

        doneSignal.await();
    }

    @Test
    public void testCompletableFutureCombine() throws Exception {
        final CountDownLatch doneSignal = new CountDownLatch(1);
        final Instant start = Instant.now();
        final CompletableFuture<String> remote1 = httpGetFuture(remote).thenCompose(this::extractHeadFuture);
        final CompletableFuture<String> remote2 = httpGetFuture("http://google.com").thenCompose(this::extractHeadFuture);
        final CompletableFuture<String> remote3 = httpGetFuture("https://duckduckgo.com").thenCompose(this::extractHeadFuture);
        remote1.thenCombineAsync(remote2, String::concat).thenCombineAsync(remote3, String::concat)
                .thenAcceptAsync(all -> {
                    printWithElapsedTime(start, all);
                })
                .whenComplete((r, e) -> doneSignal.countDown());

        doneSignal.await();
    }

    @Test
    public void testCompletableFutureAcceptEither() throws Exception {
        final CountDownLatch doneSignal = new CountDownLatch(1);
        final Instant start = Instant.now();
        final CompletableFuture<String> remote1 = httpGetFuture(remote).thenCompose(this::extractHeadFuture);
        final CompletableFuture<String> remote2 = httpGetFuture("http://google.com").thenCompose(this::extractHeadFuture);
        remote1.acceptEitherAsync(remote2, (head) -> {
            printWithElapsedTime(start, head);
        })
                .whenComplete((r, e) -> doneSignal.countDown());

        doneSignal.await();
    }

    @Test
    public void testCompletableFutureAllOf() throws Exception {
        final CountDownLatch doneSignal = new CountDownLatch(1);
        final CompletableFuture<String> remote1 = httpGetFuture(remote).thenCompose(this::extractHeadFuture);
        final CompletableFuture<String> remote2 = httpGetFuture("http://google.com").thenCompose(this::extractHeadFuture);
        final CompletableFuture<String> remote3 = httpGetFuture("https://duckduckgo.com").thenCompose(this::extractHeadFuture);
        CompletableFuture.allOf(remote1, remote2, remote3)
                .exceptionally(ex -> {
                    System.err.println(ex.getMessage());
                    return null;
                })
                .whenComplete((r, e) -> doneSignal.countDown());

        doneSignal.await();

//        final Instant start = Instant.now();
//        final String s = remote1.get();
//        printWithElapsedTime(start, s);
    }

    @Test
    public void testCompletableFutureAnyOf() throws Exception {
        final CountDownLatch doneSignal = new CountDownLatch(1);
        final Instant start = Instant.now();
        final CompletableFuture<String> remote1 = httpGetFuture(remote).thenCompose(this::extractHeadFuture);
        final CompletableFuture<String> remote2 = httpGetFuture("http://google.com").thenCompose(this::extractHeadFuture);
        final CompletableFuture<String> remote3 = httpGetFuture("https://duckduckgo.com").thenCompose(this::extractHeadFuture);
        CompletableFuture.anyOf(remote1, remote2, remote3)
                .exceptionally(ex -> {
                    System.err.println(ex.getMessage());
                    return null;
                })
                .thenAccept(head -> printWithElapsedTime(start, (String) head))
                .whenComplete((r, e) -> doneSignal.countDown());

        doneSignal.await();
    }

    @Test
    public void testCompose() throws Exception {
        final CompletableFuture<String> future1 = new CompletableFuture<>();
        final CompletableFuture<Integer> future2 = new CompletableFuture<>();
        final CompletableFuture<Integer> future3 = new CompletableFuture<>();

        future1
                .thenCombine(future2, (v1, v2) -> { System.out.println("combine[" + v1 + ", " + v2 + "]"); return v1 + v2.toString(); })
                .thenCombine(future3, (v1, v2) -> { System.out.println("combine[" + v1 + ", " + v2 + "]"); return v1 + v2.toString(); })
                .whenComplete((r, e) -> System.out.println("Result: " + r + ", Exception: " + e));

        future1.complete("value-");
//        future1.completeExceptionally(new IllegalStateException());
        future2.complete(47);
        future3.complete(11);
//        future3.completeExceptionally(new IllegalStateException());
    }

    @Test
    public void testCompletableFutureRunnable() throws Exception {
        final CountDownLatch doneSignal = new CountDownLatch(1);
        final Instant start = Instant.now();
        CompletableFuture.runAsync(() -> httpGet(remote), executor).whenComplete((r, e) -> doneSignal.countDown() );
        doneSignal.await();
        printWithElapsedTime(start, "runnable");
    }

    @Test
    public void testCompletionService() throws Exception {
        final CompletionService<String> cs = new ExecutorCompletionService<>(executor);
        cs.submit(() -> httpGet(remote));
        cs.submit(() -> httpGet("http://google.com"));

        int nWaiting = 2;
        while (nWaiting > 0) {
            final Future<String> completed = cs.poll(10L, TimeUnit.MILLISECONDS);
            if (completed != null) {
                final Instant start = Instant.now();
                final String body = completed.get();
                printWithElapsedTime(start, body);
                --nWaiting;
            }
        }
    }

    private void printWithElapsedTime(final Instant start, final String body) {
        System.out.println("printing thread: " + Thread.currentThread().getId());
        System.out.println("Elapsed: " + start.until(Instant.now(), ChronoUnit.MILLIS));
        System.out.println("Result:\n" + body);
    }


    private String extractHead(final String doc) {
        System.out.println("extracting thread: " + Thread.currentThread().getId());
        final String lowerDoc = doc.toLowerCase();
        final int headIdx = lowerDoc.indexOf("<head>");
        if (-1 == headIdx) {
            return "";
        } else {
            final int headEndIdx = lowerDoc.indexOf("</head>");
            return (-1 == headEndIdx) ? doc.substring(headIdx) : doc.substring(headIdx, headEndIdx + "</head>".length());
        }
    }

    private String httpGet(final String remote)  {
        System.out.println("httpGet thread: " + Thread.currentThread().getId());
        try {
            return readStream(new URL(remote).openStream(), 2000);
        } catch (final IOException e) {
            throw new IllegalStateException("get for url " + remote + " failed", e);
        }
    }

    private String readStream(final InputStream in, final int maxLen) throws IOException {
        final byte[] bytes = new byte[maxLen];
        int got = 0;
        int read;
        do {
//            System.out.println("readStream offset " + got + ", thread: " + Thread.currentThread().getId());
            read = in.read(bytes, got, maxLen - got);
            if (read > 0) {
                got += read;
            }
        }
        while (got < maxLen && read != -1);
        return new String(bytes, 0, got, Charset.forName("UTF-8"));
    }
}
