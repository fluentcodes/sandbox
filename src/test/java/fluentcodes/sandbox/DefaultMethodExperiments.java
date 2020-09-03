package fluentcodes.sandbox;

/**
 * Created by Werner on 16.07.2016.
 * Originated from http://www.tutorialspoint.com/java8/java8_default_methods.htm
 * Java 8 introduces a new concept of default method implementation in interfaces.
 * This capability is added for backward compatibility so that old interfaces can be used to leverage the lambda expression capability of Java 8.
 * For example, ‘List’ or ‘Collection’ interfaces do not have ‘forEach’ method declaration.
 * Thus, adding such method will simply break the collection framework implementations.
 * Java 8 introduces default method so that List/Collection interface can have a default implementation of forEach method, and the class implementing these interfaces need not implement the same.
 */
public class DefaultMethodExperiments {
    public static void main(String[] args) {
        System.out.println("Start Experiments with Default Method:");
        new DefaultMethodExperiments().startCarExample();
        System.out.println("Finished Experiments with Default Method.");
    }
        public void startCarExample (){
            Vehicle vehicle = new Car();
            vehicle.iAm();
        }


    public class Car implements Vehicle, FourWheeler {
        public void iAm(){
            Vehicle.super.iAm();
            FourWheeler.super.iAm();
            Vehicle.blowHorn();
            System.out.println("I am a car!");
        }
    }

    interface Vehicle {
        default void iAm(){
            System.out.println("I am a vehicle!");
        }

        static void blowHorn(){
            System.out.println("Blowing horn!!!");
        }
    }



    interface FourWheeler {
        default void iAm(){
            System.out.println("I am a four wheeler!");
        }
    }



}
