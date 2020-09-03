package fluentcodes.sandbox.optionals;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class OptionalTest {
    private final String someValue = "Some string";
    private final Optional<String> stringOptional = Optional.of(someValue);

    private final String nullValue = null;
    private final Optional<String> emptyOptional = Optional.ofNullable(nullValue);
//    private final Optional<String> emptyOptional = Optional.empty();

    @Test(expected = NullPointerException.class)
    public void testUnsafeCreation() throws Exception {
        final Optional<String> unsafe = Optional.of(nullValue);
    }

    @Test
    public void testToString() throws Exception {
        System.out.println("stringOptional is: " + stringOptional);
        System.out.println("emptyOptional is: " + emptyOptional);
    }

    @Test
    public void testPresenceCheck() throws Exception {
        Assert.assertTrue(stringOptional.isPresent());
        Assert.assertFalse(emptyOptional.isPresent());
    }

    @Test
    public void testIfPresent() throws Exception {
        stringOptional.ifPresent(System.out::println);
        emptyOptional.ifPresent(System.out::println);
    }

    @Test
    public void testGet() throws Exception {
        Assert.assertEquals(someValue, stringOptional.get());
        try {
            emptyOptional.get();
            Assert.fail("Should have thrown");
        } catch (final Exception e) {
            Assert.assertTrue(e instanceof NoSuchElementException);
        }
    }

    @Test
    public void testSafeGet() throws Exception {
        Assert.assertEquals(someValue, stringOptional.orElse("not set"));
        Assert.assertEquals("not set", emptyOptional.orElse("not set"));

        Assert.assertEquals(someValue, stringOptional.orElseGet(this::slowCalculation));
        Assert.assertEquals("calculated 1", emptyOptional.orElseGet(this::slowCalculation));

        Assert.assertEquals(someValue, stringOptional.orElseThrow(IllegalStateException::new));
        try {
            emptyOptional.orElseThrow(IllegalStateException::new);
            Assert.fail("Should have thrown");
        } catch (final Exception e) {
            Assert.assertTrue(e instanceof IllegalStateException);
        }
    }

    private final String[] inputs =
        { "hello world",
            "what the f...",
            "eCube rules the world",
            "hello optional",
            "world wide nepp"
        };
    @Test
    public void testMapListPatternOldStyle() throws Exception {
        final Map<String, List<String>> tokens = new HashMap<>();

        for (final String input : inputs) {
            for (final String token : input.split(" ")) {
                List<String> contexts = tokens.get(token);
                if (contexts == null) {
                    contexts = new ArrayList<>();
                    tokens.put(token, contexts);
                }
                contexts.add(input);
            }
        }

        final List<String> helloContexts = tokens.get("hello");
        Assert.assertEquals(2, helloContexts != null ? helloContexts.size() : 0);

        Assert.assertEquals(3, tokens.containsKey("world") ? tokens.get("world").size() : 0);
    }

    @Test
    public void testMapListPatternOptional() throws Exception {
        final Map<String, List<String>> tokens = new HashMap<>();

        for (final String input : inputs) {
            for (final String token : input.split(" ")) {

                Optional.ofNullable(tokens.get(token)).orElseGet(() -> {
                    final List<String> list = new ArrayList<>();
                    tokens.put(token, list);
                    return list;
                }).add(input);
            }
        }

        Assert.assertEquals(2, Optional.ofNullable(tokens.get("hello")).orElseGet(Collections::emptyList).size());
        Assert.assertEquals(3, Optional.ofNullable(tokens.get("world")).orElseGet(Collections::emptyList).size());
    }


    @Test
    public void testMapListPatternForEachAndOptional() throws Exception {
        final Map<String, List<String>> tokens = new HashMap<>();

        Arrays.asList(inputs).forEach(input ->
                Arrays.asList(input.split(" ")).forEach(token ->
                        Optional.ofNullable(tokens.get(token)).orElseGet(() -> {
                            final List<String> list = new ArrayList<>();
                            tokens.put(token, list);
                            return list;
                        }).add(input)
                )
        );

        Assert.assertEquals(2, Optional.ofNullable(tokens.get("hello")).orElseGet(Collections::emptyList).size());
        Assert.assertEquals(3, Optional.ofNullable(tokens.get("world")).orElseGet(Collections::emptyList).size());
    }


    private int counter = 0;
    private String slowCalculation() {
        return "calculated " + (++counter);
    }


    @Test
    public void testFilter() throws Exception {
        final Optional<String> anotherOptional = Optional.of("more stuff");

        stringOptional.filter(s -> s.startsWith("Some")).ifPresent(System.out::println);
        anotherOptional.filter(s -> s.startsWith("Some")).ifPresent(System.out::println);
        emptyOptional.filter(s -> s.startsWith("Some")).ifPresent(System.out::println);
    }

    @Test
    public void testMap() throws Exception {
        Assert.assertEquals(someValue.length(), stringOptional.map(String::length).orElse(0).longValue());
        Assert.assertEquals(0, emptyOptional.map(String::length).orElse(0).longValue());
    }

    @Test
    public void testFlatMap() throws Exception {
        final Computer powerMachine = new Computer(new SoundCard(new USB()));
        final Computer businessPc = new Computer(new SoundCard(null));
        final Computer notEvenSound = new Computer(null);
        final Computer lostInSpace = null;

        Assert.assertEquals(USB.version, soundUsbVersion(powerMachine));
        Assert.assertEquals("nope", soundUsbVersion(businessPc));
        Assert.assertEquals("nope", soundUsbVersion(notEvenSound));
        Assert.assertEquals("nope", soundUsbVersion(lostInSpace));
    }

    @Test
    public void testOptionalPrimitive() throws Exception {
        final OptionalInt maybeInt = OptionalInt.of(4711);
        final OptionalInt emptyInt = OptionalInt.empty();

        Assert.assertEquals(4711, maybeInt.orElse(-1));
        Assert.assertEquals(-1, emptyInt.orElse(-1));

        System.out.println("OptionalInt.orElse()");
        System.out.println(maybeInt.orElse(-2));
        System.out.println(emptyInt.orElse(-2));

        System.out.println("OptionalInt.ifPresent()");
        maybeInt.ifPresent(System.out::println);
        emptyInt.ifPresent(System.out::println);
    }


    private String soundUsbVersion(final Computer computer) {
        return Optional.ofNullable(computer)
            .flatMap(Computer::getSoundCard)
            .flatMap(SoundCard::safeGetUsb)
            .map(USB::getVersion)
            .orElse("nope");
    }

//    private String notCompilable_soundUsbVersion(final Computer computer) {
//        return Optional.ofNullable(computer)
//                .map(Computer::getSoundCard)
//                .map(SoundCard::safeGetUsb)
//                .map(USB::getVersion)
//                .orElse("nope");
//    }

    private String soundUsbVersionWithMap(final Computer computer) {
        return Optional.ofNullable(computer)
            .map(Computer::getSoundCard)
            .orElse(Optional.empty())
            .map(SoundCard::safeGetUsb)
            .orElse(Optional.empty())
            .map(USB::getVersion)
            .orElse("nope");
    }

    private static class Computer {
        private final Optional<SoundCard> soundCard;    // not recommended

        private Computer(final SoundCard soundCard) {
            this.soundCard = Optional.ofNullable(soundCard);
        }
        public Optional<SoundCard> getSoundCard() {
            return soundCard;
        }
        public SoundCard unsafeGetSoundCard() {
            return soundCard.orElse(null);
        }
    }

    private static class SoundCard {
        private final USB usb;

        private SoundCard(final USB usb) {
            this.usb = usb;
        }
        public USB getUsb() {
            return usb;
        }
        public Optional<USB> safeGetUsb() {
            return Optional.ofNullable(usb);
        }
    }

    private static class USB {
        public static String version = "2.0";

        public String getVersion() {
            return version;
        }
    }

}
