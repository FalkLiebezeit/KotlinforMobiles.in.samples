@file:UseSerializers(LocalDateTimeSerializer::class)


import kotlinx.serialization.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun main() {
    testQuiz()
    // testAnnotation()
    // testTimeDate()
    // showNytBestsellers()
}

fun testQuiz() {
    val q = Question("Wie lautet der Codename für Android 9?",
        listOf(Choice("Pie", true), // richtige Antwort
            Choice("Nougat"),    // falsche Antworten
            Choice("KitKat"),
            Choice("Oreo")))


    // Serialisieren: Objekt -> JSON
    // val s = Json.encodeToString(Question.serializer(), q)
    val s = Json.encodeToString(q)
    println(s)

    // Deserialisieren: JSON -> Objekt
    val copy = Json.decodeFromString<Question>(s)
    copy.show()

    // alternative Schreibweise
    val s2 = Json.encodeToString(Question.serializer(), q)
    val copy2 = Json.decodeFromString(Question.serializer(), s)
    // copy2.show()

    // Listen (de-)serialisieren
    val quiz = setupPool()  // List<Question>
    val quizserial = Json.encodeToString(quiz)  // String
    println(quizserial)
    val quizcopy = Json.decodeFromString<List<Question>>(quizserial)
    quizcopy.first().show()

    // Formatierungsoptionen
    val json = Json { prettyPrint = true }
    val pretty = json.encodeToString(q)
    println(pretty)

}

fun testAnnotation() {
    val obj1 = SomeData(1, 2, 3)
    val obj2 = SomeData(7)
    println(Json.encodeToString(obj1))
    println(Json.encodeToString(obj2))
}

@Serializable
class SomeData(@SerialName("xy") val a: Int,
               @Required val b: Int = 3,
               @Transient val c: Int = 4)

fun testTimeDate() {
    val data = ClassWithDate(123, "abc", LocalDateTime.now())
    val s = Json.encodeToString(data)
    println(s)
    val copy = Json.decodeFromString<ClassWithDate>("$s")
    println(copy)
}


@Serializable
data class ClassWithDate(var a: Int,
                         var b: String,
                         var c: LocalDateTime)


@ExperimentalSerializationApi
@Serializer(LocalDateTime::class)
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {

    override fun serialize(encoder: Encoder, value: LocalDateTime) =
        encoder.encodeString(value.format(DateTimeFormatter.ISO_DATE_TIME))

    override fun deserialize(decoder: Decoder) =
        LocalDateTime.parse(decoder.decodeString(),
            DateTimeFormatter.ISO_DATE_TIME)

}

