package uk.co.thecookingpot

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import io.ktor.sessions.*
import uk.co.thecookingpot.api.routes.changePassword
import uk.co.thecookingpot.api.service.BearerAuthenticationService
import uk.co.thecookingpot.login.*
import uk.co.thecookingpot.oauth.repository.ClientRepository
import uk.co.thecookingpot.login.repository.UserRepository
import uk.co.thecookingpot.oauth.repository.SessionRepository
import uk.co.thecookingpot.oauth.routes.*
import uk.co.thecookingpot.oauth.service.AuthorisationService
import uk.co.thecookingpot.oauth.service.ClientService
import uk.co.thecookingpot.oauth.service.JwtService
import uk.co.thecookingpot.oauth.service.TokenService

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val clientRepository = ClientRepository()
    val userRepository = UserRepository()
    val sessionRepository = SessionRepository()

    val authorisationService = AuthorisationService(sessionRepository)
    val jwtService = JwtService()
    val tokenService = TokenService(sessionRepository, jwtService)
    val clientService = ClientService(clientRepository)
    val authenticationService = AuthenticationService(userRepository)
    val bearerAuthenticationService = BearerAuthenticationService(sessionRepository)

    install(ContentNegotiation) {
        gson()
    }
    install(Sessions) {
        configureAuthCookie()
        configureOriginCookie()
    }
    install(Authentication) {
        configureSessionAuth()
        configureFormAuth(authenticationService)
        configureClientCredentialsAuth(clientRepository)
    }
    install(Routing) {
        home()
        login()
        authorise(authorisationService, clientService)
        token(tokenService)
        wellKnown(jwtService)
        changePassword(bearerAuthenticationService, userRepository)
        revoke(sessionRepository)
    }
}

