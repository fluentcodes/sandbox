package fluentcodes.sandbox;

import org.junit.Assert;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.stream.IntStream;

public class DateTimeTest {
    @Test
    public void testInstant() throws Exception {
        final Instant now = Instant.now();

        final Instant later = now.plus(1, ChronoUnit.HOURS);
        Assert.assertTrue(later.isAfter(now));
        Assert.assertTrue(now.isBefore(later));
        Assert.assertEquals(3600000L, now.until(later, ChronoUnit.MILLIS));
        Assert.assertEquals(3600000L, Duration.between(now, later).toMillis());
        System.out.println(Duration.between(now, now.plus(1, ChronoUnit.HOURS)).toMillis());
    }

    @Test
    public void testClock() throws Exception {
        final Clock utc = Clock.systemUTC();    // default clock for Instant
        System.out.println("def: \t" + Instant.now()
            + "\nutc: \t" + Instant.now(utc) );

        final Clock defaultZone = Clock.systemDefaultZone();    // default clock for DateTime classes
        System.out.println("def: \t" + ZonedDateTime.now()
            + "\nzoned: \t" + ZonedDateTime.now(defaultZone)
            + "\nutc: \t" + ZonedDateTime.now(utc)
            + "\nzone2: \t" + ZonedDateTime.now(utc).withZoneSameInstant(defaultZone.getZone()));

        final Duration offset = Duration.between(Instant.now(), ZonedDateTime.of(2015, 1, 1, 0, 0, 0, 0, defaultZone.getZone()).toInstant());
        final Clock clockWithOffset = Clock.offset(defaultZone, offset);
        final Clock fixedClock = Clock.fixed(Instant.now(), defaultZone.getZone());

        System.out.println("fix: \t" + ZonedDateTime.now(fixedClock) + "\toff: \t" + ZonedDateTime.now(clockWithOffset));
        Thread.sleep(4567L);
        System.out.println("fix: \t" + ZonedDateTime.now(fixedClock) + "\toff: \t" + ZonedDateTime.now(clockWithOffset));
    }


    @Test
    public void testDateFromInstant() throws Exception {

        try {
            LocalDateTime.from(Instant.now());
            Assert.fail("Should have thrown");
        } catch (final Exception e) {
            Assert.assertTrue(e instanceof DateTimeException);
        }
        final LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        Assert.assertEquals(LocalDate.now(), localDateTime.toLocalDate());
        Thread.sleep(1);
        Assert.assertTrue(LocalTime.now().isAfter(localDateTime.toLocalTime()));
    }

    @Test
    public void testLocalDateTime() throws Exception {

        final LocalDateTime now = LocalDateTime.now();
        LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN);
        System.out.println(now.plusMonths(1).getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN));

        final LocalDateTime aDayInFeb = LocalDateTime.of(2015, Month.FEBRUARY, 12, 12, 0, 0);
        System.out.println(aDayInFeb.plusDays(60));

        System.out.println(Period.between(now.toLocalDate(), now.plusSeconds(60 * 60 * 24 * 90).toLocalDate()).getDays());

        final int mondayBasedOffset = now.getDayOfWeek().getValue() - 1;
        final LocalDateTime mondayMorning = LocalDateTime.of(now.minusDays(mondayBasedOffset).toLocalDate(), LocalTime.of(0, 0));
        System.out.println("date: \t" + now);
        System.out.println("started: \t" + mondayMorning);
        System.out.println("minutes: \t" + Duration.between(mondayMorning, LocalDateTime.now()).toMinutes());
    }

    @Test
    public void testZoneId() throws Exception {
        ZoneId.getAvailableZoneIds().stream()
            .filter(z -> z.toLowerCase().contains("angeles"))
            .map(ZoneId::of)
            .forEach(z -> System.out.println(z.getId() + " - " + z.getDisplayName(TextStyle.FULL, Locale.GERMANY)));
    }

    @Test
    public void testZonedDateTime() throws Exception {
        final ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
        final ZoneId losEngelchen = ZoneId.of("America/Los_Angeles");
        System.out.println(now.withZoneSameInstant(losEngelchen));
        System.out.println(now.withZoneSameLocal(losEngelchen));
    }

    @Test
    public void testZoneDateTimeGaps() throws Exception {
        System.out.println("Sommerzeit-Umstellung");
        final ZoneId zoneIdBerlin = ZoneId.of("Europe/Berlin");
        final ZonedDateTime gap = ZonedDateTime.of(2014, 3, 30, 2, 30, 0, 0, zoneIdBerlin);
        IntStream.rangeClosed(-2, 2).mapToObj(gap::plusHours).forEach(fluentcodes.sandbox.DateTimeTest::zonedAndLocal);

        System.out.println("Winterzeit-Umstellung");
        final ZonedDateTime overlap = ZonedDateTime.of(2014, 10, 26, 2, 30, 0, 0, zoneIdBerlin);
        IntStream.rangeClosed(-2, 2).mapToObj(overlap::plusHours).forEach(fluentcodes.sandbox.DateTimeTest::zonedAndLocal);
    }

    private static void zonedAndLocal(final ZonedDateTime zoned) {
        System.out.println("Lokale Zeit: " + zoned.toLocalTime() + "; Zoned: " + zoned);
    }

    @Test
    public void testDurationVsPeriod() throws Exception {
        final ZoneId zoneIdBerlin = ZoneId.of("Europe/Berlin");
        // Meeting um 9:30:
        final ZonedDateTime meeting = ZonedDateTime.of(2014, 3, 27, 9, 30, 0, 0, zoneIdBerlin);
        System.out.println(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(meeting));

        System.out.println(LocalDate.parse("24-12-1997", DateTimeFormatter.ofPattern("d-M-yyyy")));

        // Chef krank, Meeting um eine Woche verschieben:
        System.out.println(meeting.plus(Duration.ofDays(7)));
        System.out.println(meeting.plus(Period.ofDays(7)));

        System.out.println(LocalDate.of(2015, Month.MARCH, 31).with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY)));

        final TemporalAdjuster nextLunch = t -> {
            final LocalDate date = LocalDate.from(t);
            return LocalDateTime.of((LocalTime.from(t).getHour() < 12) ? date : date.plusDays(1), LocalTime.of(12, 0));
        };
        System.out.println(LocalDateTime.of(2015, Month.MARCH, 31, 11, 30, 0).with(nextLunch));
        System.out.println(LocalDateTime.of(2015, Month.MARCH, 31, 13, 30, 0).with(nextLunch));

        try {
            System.out.println(LocalDate.of(2015, Month.MARCH, 31).with(nextLunch));
            Assert.fail("Should have thrown");
        }
        catch (final DateTimeException e) {
            // Unable to obtain LocalTime from TemporalAccessor: 2015-03-31 of type java.time.LocalDate
        }
    }


}
