package uk.co.thecookingpot.oauth.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.html.*
import io.ktor.routing.*
import io.ktor.sessions.*
import uk.co.thecookingpot.login.session.AuthPrinciple

fun Route.home() {
    authenticate {
        get("/") {
            call.respondHtml {
                +"Logged in as ".plus(call.sessions.get<AuthPrinciple>()!!.user.username)
            }
        }
    }
}