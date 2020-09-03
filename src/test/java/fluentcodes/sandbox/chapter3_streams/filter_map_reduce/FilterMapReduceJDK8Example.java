package fluentcodes.sandbox.chapter3_streams.filter_map_reduce;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Beispielprogram zeigt, wie man mit JDK 8 und Streams sowie Lambdas elegant eine Filter-Map-Reduce-Funktionalit�t implementiert.
 * 
 * @author Michael Inden
 * 
 * Copyright 2014 by Michael Inden 
 */
public class FilterMapReduceJDK8Example
{
    public static void main(String[] args)
    {
        final List<Person> persons = Arrays.asList(new Person("Stefan", LocalDate.of(1971, Month.MAY, 12)),
                                                   new Person("Micha", LocalDate.of(1971, Month.FEBRUARY, 7)),
                                                   new Person("Andi", LocalDate.of(1968, Month.JULY, 17)),
                                                   new Person("Andi Steffen", LocalDate.of(1970, Month.JULY, 17)),
                                                   new Person("Merten", LocalDate.of(1975, Month.JUNE, 14)));

        final String bornInJuly = persons.stream().filter(person -> person.birthday.getMonth() == Month.JULY)
                        .map(person -> person.name).collect(Collectors.joining(", "));

        System.out.println(bornInJuly);
    }
}