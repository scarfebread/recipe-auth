package uk.co.thecookingpot.api.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.html.*
import io.ktor.routing.*
import io.ktor.sessions.*
import uk.co.thecookingpot.login.session.UserPrincipal

fun Route.home() {
    authenticate {
        get("/") {
            call.respondHtml {
                +"Logged in as ".plus(call.sessions.get<UserPrincipal>()!!.user.username)
            }
        }
    }
}