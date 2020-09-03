package fluentcodes.sandbox.chapter3_streams.intermediate;

import fluentcodes.sandbox.chapter3_streams.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


/**
 * Beispielprogram illustriert das Filtern in einem Stream.
 * 
 * @author Michael Inden
 * 
 * Copyright 2014 by Michael Inden 
 */
public class FilterExample
{
    public static void main(final String[] args)
    {
        final List<Person> persons = new ArrayList<>();

        persons.add(new Person("Micha", 43));
        persons.add(new Person("Barbara", 40));
        persons.add(new Person("Yannis", 5));

        // final Predicate<Person> isAdult = person -> person.getAge() >= 18;
        final Stream<Person> adults = persons.stream().filter(Person::isAdult);

        adults.forEach(System.out::println);
    }
}