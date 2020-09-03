package fluentcodes.sandbox;

import java.util.Comparator;
import java.util.function.*;


public class Main_2Summary_FunctionalInterfaces {

    public static void main(String[] args) {
        // historisch: Runnable und Comperator
        Runnable r = () -> System.out.println("Runnable als Lambda.");
        Comparator<String> comp = (s1, s2) -> s1.length() - s2.length();
        
        //neu
        
        //String rein, Integer raus
        Function <String, Integer> functionVonStringToInt = str -> str.length();
        
        Predicate<String> predIsGT10 = str -> str.length() > 10;
        
        Supplier<Double> supplierRandom = () -> Math.random();
        
        Consumer<String> cons = str -> System.out.println("Consumed: "+str);
        
        //MadMax-Donnerkuppel
        BiFunction<String,Integer,Double> zweiReinUndEinGebrochenerRaus=
                (str, i)-> (str.length() / i.doubleValue());
        
        //Sonderfall der BiFunction: 3x selber Typ
        BinaryOperator<Integer> binäreAddition = (i1, i2) -> i1 + i2;
    }
}