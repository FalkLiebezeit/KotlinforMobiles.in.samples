package info.kofler

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import io.ktor.util.getDigestFunction

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

val users = mutableMapOf<String, String>()

val accounts = setupTestAccounts()

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    users["peter"] = "pw1"
    users["martinez"] = "pw2"
    users["arno"] = "pw3"

    install(Authentication) {
        basic("myauth") {
            realm = "My Server"
            validate { upCred -> // UserPasswordCredential
                if (users.containsKey(upCred.name) &&
                    users[upCred.name] == upCred.password)
                    UserIdPrincipal(upCred.name)
                else
                    null }  // führt zu 401 = Unauthorized
        }

        basic("accountAuth") {
            validate { upCred -> // UserPasswordCredential
                val account = accounts.firstOrNull { it.account == upCred.name }
                if (account is Account &&
                    account.validate(upCred.password))
                    UserIdPrincipal(upCred.name)
                else
                    null  // führt zu 401 = Unauthorized
                }
        }
    }

    routing {
        // frei zugänglich
        get ("/open") {
            call.respond("Kann ohne Login genutzt werden")
        }
        // erfordert Login
        authenticate("myauth") {
            get("/") {
                val principal = call.principal<UserIdPrincipal>()!!
                val name = principal.name
                call.respond("Hello $name!")
            }
            get("/other") {
                call.respond("Lorem ipsum")
            }
        }
    }

    routing {
        authenticate("accountAuth") {
            get("/accounts") {
                val principal = call.principal<UserIdPrincipal>()!!
                val accountname = principal.name
                val account = accounts.firstOrNull { it.account == accountname }
                call.respond("Hello ${account?.fullname}!")
            }
        }
    }

}

