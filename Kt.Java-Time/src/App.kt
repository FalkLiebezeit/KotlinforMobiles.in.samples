import java.time.*
import java.time.format.*
import java.time.temporal.*
import java.util.*
import kotlin.math.sin

fun main() {
    // Hello Date
    val date = LocalDate.now()
    println(date)  // Format 2019-12-31

    // Formatierung
    println("%d.%d.%d".format(     // Format 31.12.2020
        date.dayOfMonth,
        date.monthValue,
        date.year))
    val myformatter =     // Format 31. Dez 2020
        DateTimeFormatter.ofPattern( "d. MMM yyyy" )
    println(myformatter.format(date))

    // val myformatter2 =     // Format 31. Dez 2019
    //    DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    // Formatierung in Landessprache
    val frloc = Locale("fr", "FR")
    val frformatter =
        DateTimeFormatter.ofPattern("MMMM").withLocale(frloc)
    for (m in (1..12)) {
        val mn = Month.of(m)
        println(frformatter.format(mn))
    } // Ausgabe: janvier etc.

    // Schaltjahrtest
    if (date.isLeapYear)
        println("${date.year} ist ein Schaltjahr.")

    // Anzahl der Tage pro Monat
    for(m in (1..12)) {
        val mydate = date.withMonth(m)
        println("Monat ${mydate.month} mit " +
                "${mydate.lengthOfMonth()} Tagen.")
    }

    // Uhrzeit
    val time1 = LocalTime.now()
    println(time1)    // 20:18:28.167234

    val timeformatter =  DateTimeFormatter.ofPattern("H:m")
    println(timeformatter.format(time1))  // 20:18

    // parse
    val s = "31.12.2020 23:55"
    val informatter = DateTimeFormatter.ofPattern("d.M.yyyy H:m")
    val dt1 = LocalDateTime.parse(s, informatter)
    println(dt1)

    // of
    val geburtstag = LocalDate.of(1970, 12, 31)          // 31.12.1970
    val kinostart =  LocalTime.of(20, 30) // 20:30
    // 20.7.2019 9:30
    val pruefung =
        LocalDateTime.of(2019, 7, 20, 9, 30)
    println(pruefung)

    // Zeitpannen, Beispiel 1
    // Arbeit von 1.12.2020 8:00 bis 1.12.2017 16:15
    val startwork = LocalDateTime.of(2020,  12, 1, 8, 0)
    val endwork =   LocalDateTime.of(2020,  12, 1, 16, 15)
    val workhours = Duration.between(startwork, endwork)
    val hours = workhours.toMinutes() / 60.0
    println("Arbeitszeit in Stunden: %.2f".format(hours))

    // Beispiel 2, Präsidentschaft von Thomas Jefferson
    val jeffStart = LocalDate.of(1801, 3, 4)
    val jeffEnde  = LocalDate.of(1809, 3, 4)
    val days = ChronoUnit.DAYS.between(jeffStart, jeffEnde)
    println("Thomas Jefferson war $days Tage US-Präsident.")

    // Rechnen mit Daten und Zeiten
    val now = LocalTime.now()
    val soon = now.plusHours(1)
    val before = now.minusMinutes(30)
    println("Jetzt: $now")
    println("In einer Stunde: $soon")
    println("Vor einer halben Stunde: $before")

    val today   = LocalDate.now()
    val inAWeek = today.plusDays(7)
    val inAYear = today.plusYears(1)

    // Rechenzeit messen
    val inst = Instant.now()
    val ldt = LocalDateTime.ofInstant(inst, ZoneId.systemDefault())


    // Laufzeitmessung mit Instant.now() bzw.mit currentTimeMillis()
    val instant1 = Instant.now()
    val cur1 = System.currentTimeMillis()
    for(i in (0..1_000_000)) {
        val x = sin(i.toDouble())
    }
    val instant2 = Instant.now()
    val cur2 = System.currentTimeMillis()
    val dur = Duration.between(instant1, instant2)
    println(dur)           // z. B. PT0.08S
    println(dur.toNanos()) // z. B. 80000000
    println("Millisekunden: ${cur2 - cur1}")


    val now1 = System.currentTimeMillis()

}
