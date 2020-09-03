package fluentcodes.sandbox.datetime;

import java.time.*;

/**
 *
 * @author Charles Ohene
 */
public class Main_2Time {

    public static void main(String[] args) {
        // wieder immutable, OHNE öffentliche Konstruktoren und keine setter() sondern with
        LocalTime mittag = LocalTime.NOON;
        mittag = LocalTime.of(12,0,0,0);    //h, min, sec, nanos
        System.out.println("Mittag = "+mittag);
        
        //mutate
        
        LocalTime jetzt = LocalTime.now();
        int stunden = jetzt.getHour();
        LocalTime jetztVor3Stunden = jetzt.withHour(stunden-3);
        LocalTime jetztIn12Stunden = jetzt.plusHours(12);
        System.out.println("Jetzt: "+jetzt+"\t in12h: "+jetztIn12Stunden);
        
        
        //Duration - Zeitliches Intervall (eher zahlbasierend: 15min oder 2h etc
        Duration eightSec = Duration.ofSeconds(8);
        Duration twoSec = Duration.ofSeconds(2);
        Duration achtKomma5Sec = eightSec.plusNanos(500);
        System.out.println("8+2sec = "+eightSec.plus(twoSec));
        Duration jetztBisMittag = Duration.between(jetzt,mittag);   //evtl. negativ
        
        //Period - Zeitspanne aber datumsbasiert
        Period lebenszeit = Period.of(100,12,28);   //100 Jahre, 12 Monate, 28 Tage
        Period schwangerschaft = Period.ofMonths(9);
        LocalDate bDayOfJoe = LocalDate.of(2000, Month.JANUARY,1);
        Period zeitSeitJoesGeburt = bDayOfJoe.until(LocalDate.now());
        System.out.println("Wie alt ist Joe nochmal? "+zeitSeitJoesGeburt.getYears());
        System.out.println("Wie viele Monate sind das genau? "+zeitSeitJoesGeburt.toTotalMonths());
    }
    
}
