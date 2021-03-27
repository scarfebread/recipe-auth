package uk.co.thecookingpot.login

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.sessions.*
import uk.co.thecookingpot.login.session.AuthPrinciple
import uk.co.thecookingpot.login.session.Origin
import uk.co.thecookingpot.login.exception.InvalidCredentialsException

fun Authentication.Configuration.configureFormAuth(authenticationService: AuthenticationService) {
    form("loginForm") {
        userParamName = "username"
        passwordParamName = "password"
        challenge {
            call.respondRedirect("/login?error")
        }
        validate { credentials: UserPasswordCredential ->
            try {
                AuthPrinciple(
                    authenticationService.authenticate(credentials.name, credentials.password)
                )
            } catch (e: InvalidCredentialsException) {
                null
            }
        }
    }
}

fun Authentication.Configuration.configureSessionAuth() {
    session<AuthPrinciple> {
        challenge {
            call.sessions.set(
                Origin(call.request.uri)
            )
            call.respondRedirect("/login")
        }
        validate { session: AuthPrinciple ->
            session
        }
    }
}

fun Sessions.Configuration.configureAuthCookie() {
    cookie<AuthPrinciple>("auth-session", storage = SessionStorageMemory()) {
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
