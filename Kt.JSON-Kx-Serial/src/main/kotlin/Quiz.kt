import kotlinx.serialization.*


fun setupPool() : List<Question> {
    val tmp = mutableListOf<Question>()
    tmp += Question("Welches ist der längste Fluss der Erde?",
        listOf(Choice("Nil", true),
            Choice("Amazonas"),
            Choice("Jangtsekiang"),
            Choice("Missisippi")))
    tmp += Question("Welche der folgenden Zahlen sind Primzahlen?",
        listOf(Choice("13", true),
            Choice("2001"),
            Choice("755"),
            Choice("127", true)))
    tmp += Question("Wie lautet der Codename für Android 9?",
        listOf(Choice("Pie", true),
            Choice("Nougat"),
            Choice("KitKat"),
            Choice("Oreo")))
    tmp += Question("Welches ist das flächenmäßig größte Land der Erde?",
        listOf(Choice("Russland", true),
            Choice("Kanada"),
            Choice("USA"),
            Choice("China"),
            Choice("Indien")))
    tmp += Question("Wann wurde Kotlin 1.0 freigegeben?",
        listOf(Choice("2016", true),
            Choice("2017"),
            Choice("2018"),
            Choice("2015"),
            Choice("2019")))
    tmp += Question("Welche Ausdrücke ergeben 24?",
        listOf(Choice("2 * 3 * 4", true),
            Choice("12 + 2^2 * 3", true),
            Choice("100 - 12 * 6 - 3 * 3"),
            Choice("2^4 + 2^3", true)))
    return tmp.toList()
}

@Serializable
class Choice(val txt: String, val ok: Boolean = false)

@Serializable
class Question(val txt: String, val choices: List<Choice>) {
    // formale Sinnhaftigkeit der Frage sicherstellen
    init {
        if (choices.size < 2) {
            val msg = "Es sind zumindest zwei Antworten erforderlich.\n${this.txt}"
            throw IllegalArgumentException(msg)
        }
        if (choices.all { it.ok == false} ) {
            val msg = "Zumindest eine Antwort muss richtig sein.\n${this.txt}"
            throw IllegalArgumentException(msg)
        }
    }

    // Frage am Bildschirm präsentieren
    fun show() {
        println(txt)
        for ((idx, choice) in choices.withIndex())
            println("  (${idx + 1}) ${choice.txt}")
    }
}
