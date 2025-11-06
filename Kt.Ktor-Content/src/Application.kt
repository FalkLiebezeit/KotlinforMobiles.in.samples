@file:UseSerializers(LocalDateSerializer::class)

package info.kofler

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import java.time.LocalDate
import kotlinx.serialization.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import java.time.format.DateTimeFormatter

val model = Model("root", listOf(Item("A", "Apache"), Item("B", "Bing")))

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        json()
        /*
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
        */
    }
    routing {
        get("/") {
            call.respondText { "Ktor sample app" }
        }
        get("/v1") {
            call.respond(model)
        }
        get("/v1/item/{key}") {
            val item = model.items.firstOrNull { it.key == call.parameters["key"] }
            if (item == null)
                call.respond(HttpStatusCode.NotFound)
            else
                call.respond(item)
        }
    }
}

@Serializable
data class Model(val name: String,
                 val items: List<Item>,
                 val date: LocalDate = LocalDate.of(2018, 4, 13))

@Serializable
data class Item(val key: String,
                val value: String)

// kümmert sich um die (De-)Serialisierung von LocalDate
// siehe auch @file:UseSerializers am Beginn der Datei!
/*
@Serializer(LocalDate::class)
object LocalDateSerializer : KSerializer<LocalDate> {
    override fun serialize(encoder: Encoder, obj: LocalDate) =
        encoder.encodeString(obj.format(DateTimeFormatter.ISO_DATE))

    override fun deserialize(decoder: Decoder) =
        LocalDate.parse(decoder.decodeString(),
            DateTimeFormatter.ISO_DATE)

}
 */

@ExperimentalSerializationApi
@Serializer(LocalDate::class)
object LocalDateSerializer : KSerializer<LocalDate> {

    override fun serialize(encoder: Encoder, value: LocalDate) =
        encoder.encodeString(value.format(DateTimeFormatter.ISO_DATE))

    override fun deserialize(decoder: Decoder) =
        LocalDate.parse(decoder.decodeString(),
            DateTimeFormatter.ISO_DATE)

}