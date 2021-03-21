package uk.co.thecookingpot.authentication

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.sessions.*

fun Authentication.Configuration.configureFormAuth() {
    form("loginForm") {
        userParamName = "username"
        passwordParamName = "password"
        challenge {
            call.respondRedirect("/login")
        }
        validate { credentials: UserPasswordCredential ->
            UserIdPrincipal(credentials.name)
        }
    }
}

fun Authentication.Configuration.configureSessionAuth() {
    session<UserIdPrincipal> {
        challenge {
            call.sessions.set(
                Origin(call.request.uri)
            )
            call.respondRedirect("/login")
        }
        validate { session: UserIdPrincipal ->
            session
        }
    }
}

fun Sessions.Configuration.configureAuthCookie() {
    cookie<UserIdPrincipal>("auth-session", storage = SessionStorageMemory()) {
        cookie.path = "/"
        cookie.extensions["SameSite"] = "lax"
    }
}

fun Sessions.Configuration.configureOriginCookie() {
    cookie<Origin>("redirect-uri", storage = SessionStorageMemory()) {
        cookie.path = "/"
        cookie.extensions["SameSite"] = "lax"
    }
}
