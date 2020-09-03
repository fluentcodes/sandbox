package fluentcodes.sandbox.models;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ecube on 07.08.16.
 */
public class Person {
        public String name;
        private String city;
        public int age;

        /**
         * Constructor for collection examples
         * @param name
         * @param city
         */
        public Person (String name,String city,int age) {
            this.city=city;
            this.name=name;
            this.age=age;
        }

        public String getName() {return this.name;}
        public String getCity() {return this.city;}
        public int getAge()     {return this.age;}
        @Override
        public String toString() {
            return this.name + "/" + this.city + " (" + this.age + ")";
        }
    /**
     * CollectionExamples
     * @return
     */
    public static List<Person>getPersonList() {
        return Arrays.asList(
            new Person("walter", "new york",7),
            new Person("chen", "peking",22),
            new Person("sing", "delhi",42),
            new Person("christian", "new york",22),
            new Person("christian", "munich",16),
            new Person("christian", "munich",36));
}


}
