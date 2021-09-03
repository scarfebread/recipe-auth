package uk.co.thecookingpot.login

import io.ktor.sessions.*
import uk.co.thecookingpot.login.session.Origin
import uk.co.thecookingpot.login.session.UserPrincipal
import uk.co.thecookingpot.session.caching.SessionCache

fun Sessions.Configuration.configureAuthCookie(sessionCache: SessionCache) {
    // TODO invalidate session during revocation
    cookie<UserPrincipal>("auth-session", storage = sessionCache) {
        cookie.path = "/"
        cookie.extensions["SameSite"] = "lax"
    }
}

// TODO what's this for?
fun Sessions.Configuration.configureOriginCookie() {
    cookie<Origin>("redirect-uri", storage = SessionStorageMemory()) {
        cookie.path = "/"
        cookie.extensions["SameSite"] = "lax"
    }
}