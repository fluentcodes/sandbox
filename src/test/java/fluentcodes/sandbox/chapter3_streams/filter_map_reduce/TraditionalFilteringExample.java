package fluentcodes.sandbox.chapter3_streams.filter_map_reduce;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Beispielprogram zeigt, wie man bis vor JDK 8 eine Filter-Map-Reduce-Funktionalit�t durch 
 * Hintereinanderausf�hrung mehrerer Methoden realisieren konnte.
 * 
 * @author Michael Inden
 * 
 * Copyright 2014 by Michael Inden 
 */
public class TraditionalFilteringExample
{
    public static void main(final String[] args)
    {
        final List<Person> persons = Arrays.asList(new Person("Stefan", LocalDate.of(1971, Month.MAY, 12)),
                                                   new Person("Micha", LocalDate.of(1971, Month.FEBRUARY, 7)),
                                                   new Person("Andi", LocalDate.of(1968, Month.JULY, 17)),
                                                   new Person("Andi Steffen", LocalDate.of(1970, Month.JULY, 17)),
                                                   new Person("Merten", LocalDate.of(1975, Month.JUNE, 14)));

        final List<Person> bornInJuly = filter(persons, Month.JULY);
        final List<String> names = extractNameAttribute(bornInJuly);
        final String result = joinStrings(names, ", ");
        System.out.println(result);
    }

    private static List<Person> filter(final List<Person> persons, final Month month)
    {
        final List<Person> bornInJuly = new ArrayList<>();
        for (final Person person : persons)
        {
            if (person.birthday.getMonth() == Month.JULY)
            {
                bornInJuly.add(person);
            }
        }
        return bornInJuly;
    }

    private static List<String> extractNameAttribute(final List<Person> persons)
    {
        final List<String> names = new ArrayList<>();
        for (final Person person : persons)
        {
            names.add(person.name);
        }
        return names;
    }

    private static String joinStrings(final List<String> names, final String delimeter)
    {
        final StringBuilder sb = new StringBuilder();

        final Iterator<String> it = names.iterator();
        while (it.hasNext())
        {
            sb.append(it.next());
            if (it.hasNext())
            {
                sb.append(delimeter);
            }
        }

        return sb.toString();
    }
}