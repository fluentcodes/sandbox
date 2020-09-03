package fluentcodes.sandbox.streams;

import fluentcodes.sandbox.SimpleLogger;
import fluentcodes.sandbox.models.Person;
import org.junit.Test;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionTest {

    private static final fluentcodes.sandbox.SimpleLogger logger = System.out::println;

    private final Collection<String> strings = new ArrayList<>(Arrays.asList("0.23e2", "873", "-312.1", "43.3", "653", "-12", "734e-1"));


    /**
     * Different String examples using
     * String related streams
     */
    //examples from http://winterbe.com/posts/2015/03/25/java8-examples-string-number-math-files/
    @Test
    public void testStringExamples() {
        String concatenated = String.join(":", "foobar", "foo", "bar");
        System.out.println("concatenated: " + concatenated);


        concatenated = "foobar:foo:bar"
                .chars()
                .distinct()
                .mapToObj(c -> String.valueOf((char)c))
                .sorted()
                .collect(Collectors.joining());

        System.out.println("concatenated1: " + concatenated);

        concatenated = Pattern.compile(":")
                .splitAsStream("foobar:foo:bar")
                .filter(s -> s.contains("bar"))
                .sorted()
                .collect(Collectors.joining(":"));

        System.out.println("concatenated2: " + concatenated);

        Pattern pattern = Pattern.compile(".*@gmail\\.com");
        long count = Stream.of("bob@gmail.com", "alice@hotmail.com")
                .filter(pattern.asPredicate())
                .count();

        System.out.println("filtered entries " + count);
    }

    /**
     * Several collections tests using city a
     */
    @Test
    public void testCollectPersonByCity (){


        List<Person> persons = Person.getPersonList();

        logger.log("List collector for filtered names starting with c");
        List<Person> filtered =
            persons
                .stream()
                .filter(p -> p.getName().startsWith("c"))
                .collect(Collectors.toList());

        filtered.forEach(p->System.out.println(p.getName() + " " + p.getCity() + " " + p.getAge()));

        Map<String, List<Person>> collect =
                persons
                        .stream()
                        .collect(
                                Collectors
                                        .groupingBy(Person::getCity));
        System.out.println(collect);

        //Single entry for christion and munich as its a set
        Map<String, Set<String>> collect1 =
                persons
                        .stream()
                        .collect(
                                Collectors
                                        .groupingBy(Person::getCity,Collectors.mapping(Person::getName, Collectors.toSet())));
        System.out.println(collect1);


        //Double entry for christion and munich as its a list
        Map<String, List<String>> collect2 =
                persons
                        .stream()
                        .collect(
                                Collectors
                                        .groupingBy(Person::getCity, Collectors.mapping(Person::getName, Collectors.toList())));
        System.out.println(collect2);

        //Double entry for christion and munich with direct print
        persons.stream()
                .collect(
                        Collectors
                                .groupingBy(Person::getCity, Collectors.mapping(Person::getName, Collectors.toList())))
        .forEach((key,value)->System.out.println(key + " " + value));
    }

    /**
     * Several collections tests using city a
     */
    @Test
    public void testCollectPersonByAge () {
        List<Person> persons = Person.getPersonList();

        logger.log("Collecting for the same age:");
        Map<Integer, List<Person>> personsByAge = persons
                .stream()
                .collect(Collectors.groupingBy(p -> p.getAge()));

        personsByAge
                .forEach((age, p) -> System.out.format("age %s: %s\n", age, p));

        logger.log("Now computing average:");
        //----------------------------------
        Double averageAge = persons
                .stream()
                .collect(Collectors.averagingInt(p -> p.getAge()));
        logger.log("Average age=" + averageAge.toString());


        logger.log("Using IntSummaryStatistics:");
        //----------------------------------------
        IntSummaryStatistics ageSummary =
                persons
                        .stream()
                        .collect(Collectors.summarizingInt(p -> p.getAge()));

        System.out.println("IntSummarayStatistics:" + ageSummary);


        logger.log("Joins all persons into a single string:");
        //----------------------------------
        String phrase = persons
                .stream()
                .filter(p -> p.getAge() >= 18)
                .map(p -> p.getName())
                .collect(Collectors.joining(" and ", "In Germany ", " are of legal age."));

        logger.log(phrase);


        logger.log("toMap:");
        //-----------
        Map<Integer, String> map = persons
                .stream()
                .collect(Collectors.toMap(
                        p -> p.getAge(),
                        p -> p.getName(),
                        (name1, name2) -> name1 + ";" + name2));

        System.out.println("MapResult: " + map);


        logger.log("Own collector example with uppercase names:");
        //----
        Collector<Person, StringJoiner, String> personNameCollector =
                Collector.of(
                        () -> new StringJoiner(" | "),          // supplier
                        (j, p) -> j.add(p.getName().toUpperCase()),  // accumulator
                        (j1, j2) -> j1.merge(j2),               // combiner
                        StringJoiner::toString);                // finisher

        String names = persons
                .stream()
                .collect(personNameCollector);

        System.out.println(names);


    }








}
