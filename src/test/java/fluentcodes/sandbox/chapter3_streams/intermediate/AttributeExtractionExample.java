package fluentcodes.sandbox.chapter3_streams.intermediate;

import fluentcodes.sandbox.chapter3_streams.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


/**
 * Beispielprogram zeigt die Extraktion von Attributen als eine Intermediate Operation. 
 * 
 * @author Michael Inden
 * 
 * Copyright 2014 by Michael Inden 
 */
public class AttributeExtractionExample
{
    public static void main(final String[] args)
    {
        final List<Person> persons = new ArrayList<>();
        persons.add(new Person("Barbara", 40));
        persons.add(new Person("Yannis", 5));

        // Mapping auf Name mit Lambda
        final Stream<Person> adults = persons.stream().filter(Person::isAdult);
        final Stream<String> namesStream = adults.map(person -> person.getName());

        // Mapping auf Alter mit Methodenreferenz
        final Stream<Integer> agesStream = persons.stream().map(Person::getAge).filter(age -> age >= 18);

        namesStream.forEach(System.out::println);
        agesStream.forEach(System.out::println);
    }
}