package info.kofler

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.serialization.*

// Todo-Klasse
@Serializable
data class Todo(var id: Int = 0,
                val txt: String,
                val priority: Int) {
    // Achtung, ist nicht gleichwertig zu
    // var id: Int = nextId()
    // wegen Serialisierung!
    init {
        if(id == 0)
            id = nextId()
    }

    companion object {
        private var counter = AtomicInteger()
        private fun nextId() = counter.incrementAndGet().toInt()
    }
}

// Todo-Routing
inline fun Routing.todoRouting() {
    get("/") {
        call.respondText {
            "To-do test app, use url /todos"
        }
    }
    route("/todos") {
        // alle To-dos auslesen (geordnet nach Priorität)
        get("/") {
            call.respond(todos.sortedBy { it.priority })
        }

        // einen To-do-Eintrag auslesen
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val todo = todos.firstOrNull { it.id == id }
            if (todo is Todo) {
                call.respond(todo)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        // neuen To-do-Eintrag speichern; falls es Eintrag
        // mit gleicher ID gibt, wird dieser überschrieben
        post("/") {
            try {
                val newTodo = call.receive<Todo>()
                // To-do-Einträge mit gleicher ID entfernen
                todos.removeAll { it.id == newTodo.id }
                todos += newTodo
                call.respond(newTodo)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        // To-do-Eintrag ändern
        patch("/") {
            val changedTodo = call.receive<Todo>()
            if (todos.removeAll({ it.id == changedTodo.id })) {
                todos.add(changedTodo)
                call.respond(changedTodo)
            } else
                call.respond(HttpStatusCode.NotFound)
        }
        // To-do-Eintrag löschen
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if(id is Int && todos.removeAll { it.id == id })
                call.respond(HttpStatusCode.OK)
            else
                call.respond(HttpStatusCode.NotFound)
        }
    }
}