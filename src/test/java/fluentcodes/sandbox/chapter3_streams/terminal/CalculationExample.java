package fluentcodes.sandbox.chapter3_streams.terminal;

import fluentcodes.sandbox.chapter3_streams.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

/**
 * Beispielprogamm zeigt, wie man verschiedene Berechnungen ausf�hren kann.
 * 
 * @author Michael Inden
 * 
 * Copyright 2014 by Michael Inden 
 */
public class CalculationExample
{
    public static void main(final String[] args)
    {
        final List<Person> persons = new ArrayList<>();
        persons.add(new Person("Ten", 10));
        persons.add(new Person("Twenty", 20));
        persons.add(new Person("Thrirty", 30));
        persons.add(new Person("Forty", 40));

        // Anzahl an Personen, deren Name mit 'T' statet  
        final long count = persons.stream().filter(person -> person.getName().startsWith("T")).count();
        System.out.println("count: " + count);

        // Summe berechnen  
        final int sum = persons.stream().filter(person -> person.getName().startsWith("T")).mapToInt(Person::getAge)
                        .sum();
        System.out.println("sum:   " + sum);

        // Durchschnitt berechnen 
        final OptionalDouble avg = persons.stream().filter(person -> person.getName().startsWith("X"))
                        .mapToInt(Person::getAge).average();
        System.out.println("avg:   " + avg);
    }
}