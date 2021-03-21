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
                head {
                    link(rel = "stylesheet", href = "https://fonts.googleapis.com/css?family=Didact Gothic")
                    link(rel = "stylesheet", href = "http://localhost:8080/css/library.css", type = "text/css")
                    link(rel = "stylesheet", href = "http://localhost:8080/css/login.css", type = "text/css")
                }
                body {
                    div(classes = "container") {
                        div(classes = "form") {
                            h1 { +"Recipe Auth" }
                            form(method = FormMethod.post) {
                                label { +"Username" }
                                br
                                textInput(name = "username")
                                br
                                label { +"Password" }
                                br
                                passwordInput(name = "password")
                                br
                                button(classes = "button") { +"LOGIN" }
                            }
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