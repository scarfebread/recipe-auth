package uk.co.thecookingpot.oauth.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.html.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import kotlinx.html.*
import uk.co.thecookingpot.login.session.UserPrincipal
import uk.co.thecookingpot.login.session.Origin

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
                                if (call.request.queryParameters["error"] != null) {
                                    div(classes = "error") { +"Invalid username and password!" }
                                }
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
                    call.principal<UserPrincipal>()
                )

                call.sessions.get<Origin>()?.also { origin -> call.respondRedirect(origin.uri) } ?: call.respondRedirect("/")
            }
        }
    }
}