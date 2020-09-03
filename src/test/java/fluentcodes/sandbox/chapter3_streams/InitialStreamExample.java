package fluentcodes.sandbox.chapter3_streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Beispielprogramm zur Demonstration der drei Verarbeitungschritte: 
 * Create, Intermediate, Terminal
 * Quelle -> Create Stream  ->  
 *      IntermediateOP1, OP2,..OPn -> 
 *          TerminalOP (Startet Verarbeitungskette & resultiert in finalem Ergebnis)
 */
public class InitialStreamExample
{
    public static void main(String[] args)
    {

        final List<Person> persons = createTestData();

        final List<Person> adults = persons.stream(). // Create  
                        filter(Person::isAdult).      // Intermediate  
                collect(Collectors.toList()); // Terminal

        System.out.println(adults);
    }

    private static List<Person> createTestData()
    {
        final List<Person> persons = new ArrayList<>();
        persons.add(new Person("Mike", 43));
        persons.add(new Person("Mike", 42));
        persons.add(new Person("Merten", 38));
        persons.add(new Person("Max", 5));
        persons.add(new Person("Moritz", 7));
        return persons;
    }
}