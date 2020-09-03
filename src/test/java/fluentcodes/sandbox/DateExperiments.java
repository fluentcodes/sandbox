package fluentcodes.sandbox;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;


/**
 * Created by Werner on 16.07.2016.
 * Java 8 introduces a new date-time API under the package java.time. Following are some of the important classes introduced in java.time package −
 * Local − Simplified date-time API with no complexity of timezone handling.
 * Zoned − Specialized date-time API to deal with various timezones.
 * The examples are based on http://www.tutorialspoint.com/java8/java8_datetime_api.htm
 */
public class DateExperiments {
    public static void main(String[] args) {
        System.out.println("Start Experiments with Lamda:");
        testLocalDateTime();
        testLocalTime();
        testZonedDateTime();
        testChromoUnits();
        testPeriod();
        testDuration();
        testAdjusters();
        testBackwardCompatability();
        System.out.println("Finished Experiments with Lamda.");
    }


    public static void testLocalDateTime(){
        System.out.println("Now testing LocalDateTime");
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Current DateTime: " + currentDateTime);

        LocalDate date1 = currentDateTime.toLocalDate();
        System.out.println("date1: " + date1);

        Month month = currentDateTime.getMonth();
        int day = currentDateTime.getDayOfMonth();
        int seconds = currentDateTime.getSecond();

        System.out.println("Month: " + month + ", day: " + day +", seconds: " + seconds);

        LocalDateTime date2 = currentDateTime.withDayOfMonth(10).withYear(2012);
        System.out.println("date2: " + date2);

        //12 december 2014
        LocalDate date3 = LocalDate.of(2014, Month.DECEMBER, 12);
        System.out.println("date3: " + date3);

    }

    // http://www.tutorialspoint.com/java8/java8_datetime_api.htm
    public static void testLocalTime(){
        System.out.println("Now testing LocalTime");

        //22 hour 15 minutes
        LocalTime time1 = LocalTime.of(22, 15);
        System.out.println("time2: " + time1);

        //parse a string
        LocalTime time2 = LocalTime.parse("20:15:30");
        System.out.println("time2: " + time2);
    }

    public static void testZonedDateTime(){
        System.out.println("Now testing Zoned Date Time");
        // Get the current date and time
        ZonedDateTime dateTimeAsia = ZonedDateTime.parse("2007-12-03T10:15:30+05:30[Asia/Karachi]");
        System.out.println("dateTimeAsia: " + dateTimeAsia + " " + dateTimeAsia.getZone());
        ZonedDateTime dateEurope = ZonedDateTime.of(dateTimeAsia.toLocalDateTime(),ZoneId.systemDefault());
        System.out.println("dateLocal: " + dateEurope + " " + dateEurope.getZone());
        ZoneId id = ZoneId.of("Europe/Paris");
        System.out.println("ZoneId: " + id);

        ZoneId currentZone = ZoneId.systemDefault();
        System.out.println("CurrentZone: " + currentZone);
    }

    /**
     * java.time.temporal.ChronoUnit enum is added in Java 8 to replace the integer values used in old API to represent day, month, etc
     */
    public static void testChromoUnits(){
        System.out.println("Now testing Chromo Units.");
        //Get the current date
        LocalDate today = LocalDate.now();
        System.out.println("Current date: " + today);

        //add 1 week to the current date
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
        System.out.println("Next week: " + nextWeek);

        //add 1 month to the current date
        LocalDate nextMonth = today.plus(1, ChronoUnit.MONTHS);
        System.out.println("Next month: " + nextMonth);

        //add 1 year to the current date
        LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
        System.out.println("Next year: " + nextYear);

        //add 10 years to the current date
        LocalDate nextDecade = today.plus(1, ChronoUnit.DECADES);
        System.out.println("Date after ten year: " + nextDecade);
    }

    /**
     * With Java 8, two specialized classes are introduced to deal with the time differences −
     * Period − It deals with date based amount of time.
     */

    public static void testPeriod(){

        //Get the current date
        LocalDate date1 = LocalDate.now();
        System.out.println("Current date: " + date1);

        //add 1 month to the current date
        LocalDate date2 = date1.plus(1, ChronoUnit.MONTHS);
        System.out.println("Next month: " + date2);

        Period period = Period.between(date2, date1);
        System.out.println("Period: " + period + ": days= " + period.getDays());
    }
    /**
     * With Java 8, two specialized classes are introduced to deal with the time differences −
     * Duration − It deals with time based amount of time.
     */
    public static void testDuration(){
        LocalTime time1 = LocalTime.now();
        Duration twoHours = Duration.ofHours(2);

        LocalTime time2 = time1.plus(twoHours);
        Duration duration = Duration.between(time1, time2);

        System.out.println("Duration: " + duration + ": sec= " + duration.getSeconds());
    }

    /**
     * TemporalAdjuster is used to perform the date mathematics. For example, get the "Second Saturday of the Month" or "Next Tuesday".
     */

    public static void testAdjusters(){

        //Get the current date
        LocalDate date1 = LocalDate.now();
        System.out.println("Current date: " + date1);

        //get the next tuesday
        LocalDate nextTuesday = date1.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
        System.out.println("Next Tuesday on : " + nextTuesday);

        //get the second saturday of next month
        LocalDate firstInYear = LocalDate.of(date1.getYear(),date1.getMonth(), 1);
        LocalDate secondSaturday = firstInYear.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)).with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        System.out.println("Second Saturday on : " + secondSaturday);
    }


    /**
     * A toInstant() method is added to the original Date and Calendar objects, which can be used to convert them to the new Date-Time API.
     * Use an ofInstant(Insant,ZoneId) method to get a LocalDateTime or ZonedDateTime object.
     */

    public static void testBackwardCompatability(){

        //Get the current date
        Date currentDate = new Date();
        System.out.println("Current date: " + currentDate);

        //Get the instant of current date in terms of milliseconds
        Instant now = currentDate.toInstant();
        ZoneId currentZone = ZoneId.systemDefault();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, currentZone);
        System.out.println("Local date: " + localDateTime);

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, currentZone);
        System.out.println("Zoned date: " + zonedDateTime);
    }
}
