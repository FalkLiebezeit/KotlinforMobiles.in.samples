package info.kofler

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.PipelineContext
import java.time.LocalDateTime
import java.time.ZonedDateTime
import kotlinx.css.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    // auf den Umgang mit speziellen HTTP-Zuständen (hier 404)
    // Einfluss nehmen
    install(StatusPages) {
        status(HttpStatusCode.NotFound) {
            // it enthält HttpStatusCode-Objekt
            call.respondText { "my own 404 handler" }
        }
    }

    // Definition der Routing-Regeln
    routing {
        get("/") {
            val pc = this
            call.respondText { "allgemeiner GET ${(1..1000).random()}" }
        }
        get("/a") {
            call.respondText { "GET /a" }
        }
        get("/item") {
            call.respondText { "GET /item" }
        }
        get("/para/{p1}/{p2}/{p3?}") {
            val p1 = call.parameters["p1"]  // jeweils Datentyp String?
            val p2 = call.parameters["p2"]
            val p3 = call.parameters["p3"]
            call.respondText { "p1=$p1 p2=$p2 p3=$p3" }
        }
        get("/dir1/dir2") {
            val r = call.request
            val version= r.httpVersion     // "HTTP/1.1"
            val method = r.httpMethod // GET, POST...
            val uri = r.uri                // Pfad (ohne Hostname)
            val scheme = r.origin.scheme   // "http" oder "https"
            val host = r.host()            // lokaler Hostname oder IP-Adresse
            val port = r.port()               // Port
            val path = r.path()            // Pfad (ohne Hostname)
            val document = r.document()    // letztes Segment des Pfads
            val remoteHost = r.origin.remoteHost  // externer Host

            // Query-Parameter auswerten
            val query = r.queryString()    // URL-Parameter (leer?)
            val para = r.queryParameters
            val p1 = para["p1"]
            val p2 = para["p2"]

            val msg = """
                version=$version
                method=$method
                scheme=$scheme
                host=$host
                port=$port
                uri=$uri
                path=$path
                document=$document
                query=$query
                p1=$p1 p2=$p2
                remoteHost=$remoteHost
            """.trimIndent()
            call.respondText { msg }
        }
        post("form") {
            val para = call.receiveParameters()
            val p1 = para["p1"]
            val p2 = para["p2"]
            call.respondText { "p1=$p1 p2=$p2" }
        }
        get("/header") {
            // zuerst HTTP-Header bzw. -Status ändern
            val resp = call.response
            resp.header("additional_header", "my value")
            resp.lastModified(ZonedDateTime.now())
            resp.expires(LocalDateTime.now())
            resp.status(HttpStatusCode.OK)
            // dann respond-Methode aufrufen
            call.respondText { "lorem ipsum" }
        }
        moreRouting()
        route("/dir") {
            route("subdir") {
                get  { call.respondText { "GET dir/subdir" } }
                put  { call.respondText { "PUT dir/subdir" } }
            }
        }

        get("/html") {
            call.respondHtml {

            }
        }

        get("/css") {
            call.respondCss {

            }
        }

        // liefert Dateien aus resources/static aus
        // z.B. static/foto.jpg oder static/ktor_logo.svg
        static("/static") {
            resources("static")
        }
        static("/images") {
            files("/crypt/home/kofler/screenshots")
        }

        // Catch-all
        /*
        get("{...}") {
            call.response.status(HttpStatusCode.NotFound)  // 404
            call.respondText { "gewünschte Seite nicht gefunden" }
        }
        */
    }

}

inline fun Routing.moreRouting() {
    get("/c") {}
    post("/d") {
        getD()
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.getD() {
    val req = call.request
    //  ...
}

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}