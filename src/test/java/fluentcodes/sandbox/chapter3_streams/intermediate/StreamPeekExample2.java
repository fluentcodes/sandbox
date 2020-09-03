package fluentcodes.sandbox.chapter3_streams.intermediate;


import fluentcodes.sandbox.chapter3_streams.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Beispielprogamm zeigt die Methode peek() zur Inspektion von Verarbeitungsschritten.
 * 
 * @author Michael Inden
 * 
 * Copyright 2014 by Michael Inden 
 */
public class StreamPeekExample2
{
    public static void main(final String[] args)
    {
        final List<Person> persons = createDemoData();

        System.out.println("Protokollierung jedes einzelnen Schritts");
        final Stream<Person> stream = persons.stream();

        final Stream<String> allMikes = stream.peek(str -> System.out.println("In: " + str))
                .filter(Person::isAdult).peek(System.out::println)
                .map(Person::getName).peek(System.out::println)
                .filter(name -> name.startsWith("Mi")).peek(System.out::println)
                .map(String::toUpperCase);

        // Löst die Verarbeitung aus
        System.out.println("Filter for 'Mi':");
        allMikes.forEach(str -> System.out.println("Erg: " + str));

        System.out.println("---- nochmal");

        final Function<Stream<Person>,Stream<String>> myFilter =
                str -> str.filter(Person::isAdult).peek(System.out::println)
                        .map(Person::getName).peek(System.out::println)
                        .filter(name -> name.startsWith("Mi")).peek(System.out::println)
                        .map(String::toUpperCase);
        myFilter.apply(persons.stream()).forEach(str -> System.out.println("Erg2: " + str));
    }

    private static List<Person> createDemoData()
    {
        final List<Person> persons = new ArrayList<>();
        persons.add(new Person("Michael", 43));
        persons.add(new Person("Max", 5));
        persons.add(new Person("Merten", 38));
        persons.add(new Person("Moritz", 7));
        persons.add(new Person("Micha", 42));
        return persons;
    }
}
