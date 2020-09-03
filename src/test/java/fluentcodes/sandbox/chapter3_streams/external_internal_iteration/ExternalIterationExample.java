package fluentcodes.sandbox.chapter3_streams.external_internal_iteration;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Beispielprogramm zur Demonstration der externe Itration auf 3 verschiedene Arten.
 * 
 * @author Michael Inden
 * 
 * Copyright 2014 by Michael Inden 
 */
public class ExternalIterationExample
{
    public static void main(final String[] args)
    {
        final List<String> names = Arrays.asList("Andi", "Mike", "Ralph", "Stefan");

        // Klassische Variante mit Iterator ... 
        final Iterator<String> it = names.iterator();
        while (it.hasNext())
        {
            final String name = it.next();
            System.out.println(name);
        }

        // ... oder alternativ indiziertem Zugriff  
        for (int i = 0; i < names.size(); i++)
        {
            System.out.println(names.get(i));
        }

        // JDK 5-Schreibweise mit for-each  
        for (final String name : names)
        {
            System.out.println(name);
        }
    }
}