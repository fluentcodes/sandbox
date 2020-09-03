package fluentcodes.sandbox.chapter3_streams.terminal;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import static java.util.stream.Collectors.*;

/**
 * Beispielprogamm demonstriert verschiedene spezielle Gruppierungs- und Kombinationsmetoden.
 * 
 * @author Michael Inden
 * 
 * Copyright 2014 by Michael Inden 
 */
public class CollectorsSpecialExample
{
    public static void main(final String[] args)
    {
        final List<String> names = Arrays.asList("Stefan", "Ralph", "Andi", "Mike", "Florian", "Michael", "Sebastian");

        String joined = names.stream().sorted().collect(joining(", "));

        Map<Integer, List<String>> grouped = names.stream().collect(groupingBy(String::length));
        Map<Integer, Long> counting = names.stream().collect(groupingBy(String::length, counting()));

        Map<Boolean, List<String>> partition = names.stream().filter(str -> str.contains("i"))
                        .collect(partitioningBy(str -> str.length() > 4));

        System.out.println(joined);
        System.out.println(grouped);
        System.out.println(counting);
        System.out.println(partition);

        final double[] values = {};
        final OptionalDouble average = Arrays.stream(values).average();
        System.out.println(average);
    }
}
