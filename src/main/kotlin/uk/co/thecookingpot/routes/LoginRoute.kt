package uk.co.thecookingpot.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.html.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import kotlinx.html.*
import uk.co.thecookingpot.authentication.Origin

fun Route.login() {
    route("/login") {
        get {
            call.respondHtml {
                body {
                    form(method = FormMethod.post) {
                        textInput(name = "username")
                        br
                        passwordInput(name = "password")
                        br
                        submitInput {
                            value = "LOGIN"
                        }
                    }
                }
            }
        }

        authenticate("loginForm") {
            post {
                call.sessions.set(
                    call.principal<UserIdPrincipal>()
                )

                call.sessions.get<Origin>()?.also { origin -> call.respondRedirect(origin.uri) } ?: call.respondRedirect("/")
            }
        }
    }
}