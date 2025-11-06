package info.kofler

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import kotlinx.css.*
import kotlinx.html.stream.appendHTML

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    // println(createHtml())
    // println(createCss())

    routing {
        // erstes HTML-Beispiel
        get("/") {
            call.respondHtml {
                head {
                    title { +"My title"}
                }
                body {
                    h1 { +"Überschrift" }
                    p { +"Hello "
                        +"World!" }
                }
            }
        }

        // Formular mit variabler Größe
        get("/form/{max}") {
            val max = call.parameters["max"]?.toIntOrNull() ?: 1
            call.respondHtml {
                body {
                    myform(max)
                    /* // dieser Code wurde in die Funktion myform()
                       // ausgelagert
                    form(action = "/form", method = FormMethod.post) {
                        for (i in (1..max))
                            br { +"$i "
                                textInput(name = "id$i")
                            }
                        p { submitInput() }
                    }
                    */
                }
            }
        }

        // Formulardaten verarbeiten
        post("/form") {
            val para = call.receiveParameters()
            var msg = "Formulardaten:\n"
            for ((paraname, value) in para.entries()) {
                msg += "$paraname = ${value}\n"
            }
            call.respondText { msg }
        }

        // HTML-Templates ausprobieren
        get("/html-template") {
            call.respondHtmlTemplate(BasicTemplate()) {
                mytitle { +"My title" }
                content {
                    p { +"Lorem ipsum" }
                    ul { li { +"First point" }
                         li { +"Second" } }
                }
            }
        }

        get("/withstyle.html") {
            call.respondHtml {
                head {
                    title { +"HTML with style"}
                    link(href="/mystyle.css", type="text/css", rel="stylesheet")
                }
                body {
                    h1 { +"H1 header" }
                    p("myclass") { +"Lorem ipsum" }
                }
            }
        }
        get("/mystyle.css") {
            call.respondCss {
                body {
                    fontFamily = "sans-serif"
                }
                h1 {
                    color = Color.red
                }
                rule("p.myclass") {
                    borderStyle = BorderStyle.dotted
                }
            }
        }
    }
}

// HTML-Code aus Lambda-Ausdruck auslagern
inline fun BODY.myform(max: Int) {
    form(action = "/form", method = FormMethod.post) {
        for (i in (1..max))
            br { +"$i "
                textInput(name = "id$i")
            }
        p { submitInput() }
    }
}

// HTML-Templates ausprobieren
class BasicTemplate : Template<HTML> {
    val mytitle = Placeholder<HTMLTag>()
    val content = Placeholder<HtmlBlockTag>()

    override fun HTML.apply() {
        head { title { insert(mytitle) } }
        body { h1 { insert(mytitle) }
               insert(content)
        }
    }
}


// Code, der vom Ktor-Plugin eingefügt wurde
fun FlowOrMetaDataContent.styleCss(builder: CSSBuilder.() -> Unit) {
    style(type = ContentType.Text.CSS.toString()) {
        +CSSBuilder().apply(builder).toString()
    }
}
fun CommonAttributeGroupFacade.style(builder: CSSBuilder.() -> Unit) {
    this.style = CSSBuilder().apply(builder).toString().trim()
}
suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}

fun createHtml() =  // liefert String zurück
    buildString {
        appendln("<!DOCTYPE html>")
        appendHTML().html {
            head { title { +"My title" } }
            body {
                p { +"Lorem ipsum" }
            }
        }
    }

fun createCss() =  // String
  CSSBuilder().apply {
      body {
          backgroundColor = Color("#040703")
      }
  }.toString()