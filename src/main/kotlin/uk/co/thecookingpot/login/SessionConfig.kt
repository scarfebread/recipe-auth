package uk.co.thecookingpot.login

import io.ktor.sessions.*
import uk.co.thecookingpot.login.session.UserPrincipal
import uk.co.thecookingpot.user.session.UserSessionCache

// TODO invalidate session during revocation
fun Sessions.Configuration.configureAuthCookie(sessionCache: UserSessionCache) {
    cookie<UserPrincipal>("auth-session", storage = sessionCache) {
        cookie.path = "/"
        cookie.extensions["SameSite"] = "lax"
    }
}
