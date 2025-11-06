package info.kofler

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.receive
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.serialization.json.JsonConfiguration

import java.util.concurrent.atomic.AtomicInteger


val todos = mutableListOf<Todo>()

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    todos.add(Todo(txt = "Ktor lernen", priority = 1))
    todos.add(Todo(txt = "Zahnarzttermin vereinbaren", priority = 2))

    // install(DefaultHeaders)
    // install(CallLogging)

    // Default-Serialisierung
    install(ContentNegotiation) { json() }

    // Serialisierung mit Optionen
    /*
    install(ContentNegotiation) {
        json(JsonConfiguration(prettyPrint = true))
    }

     */
    routing {
        todoRouting()
    }

}

