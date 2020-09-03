package fluentcodes.sandbox.lamdas;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class MainLambdaIntro {

    public static void main(String[] args) {
        // Klassisch via annoyme innere Klasse, sortiert nach Länge
        Comparator<String> compLength = new Comparator<String>() {

            @Override
            public int compare(String s1, String s2) {
                return s1.length() - s2.length();
            }
        };        
        List list = Arrays.asList("Joe", "Der Großvater", "Peter Pan", "Heidi");
        Collections.sort(list, compLength);           // 
        System.out.println("Via Länge: "+list);

        //Lambda, sortiert lexikographisch
        Comparator<String> compLambdaLexiko1 = (String s1, String s2) -> {
            return s1.compareTo(s2);
        };

        Collections.sort(list, compLambdaLexiko1);      
        System.out.println("Lexikographisch1: "+list);
        
        // Kürzere Alternative: Ohne Parametertyp
        Comparator<String> compLambdaLexiko2 = (s1,s2) -> {
            return s1.compareTo(s2);
        };
        
        // Kürzere Alternative: Ohne return und Klammern falls Einzelanweisung
        Comparator<String> compLambdaLexiko3 = (s1,s2) ->  s1.compareTo(s2);
        
        Collections.sort(list, compLambdaLexiko3);
        System.out.println("Lexikographisch3: "+list);
        
        //Neuerung des ListInterfaces seit Java8
        list.sort(compLambdaLexiko3);
        
        // Anderes Beispiel mit Consumer.accept als Lamda und 
        // List implements Iterable mit default Methode forEach(Consumer)
        Consumer<String> stringConsumer = s -> System.out.println("Accept & Consumed: "+s);
        list.forEach(stringConsumer);
    }
}