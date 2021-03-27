package uk.co.thecookingpot

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.sessions.*
import uk.co.thecookingpot.login.configureAuthCookie
import uk.co.thecookingpot.login.configureFormAuth
import uk.co.thecookingpot.login.configureOriginCookie
import uk.co.thecookingpot.login.configureSessionAuth
import uk.co.thecookingpot.oauth.repository.AuthCodeRepository
import uk.co.thecookingpot.oauth.repository.ClientRepository
import uk.co.thecookingpot.login.repository.UserRepository
import uk.co.thecookingpot.oauth.routes.authorise
import uk.co.thecookingpot.oauth.routes.home
import uk.co.thecookingpot.oauth.routes.login
import uk.co.thecookingpot.oauth.routes.token
import uk.co.thecookingpot.login.AuthenticationService
import uk.co.thecookingpot.oauth.service.AuthorisationService
import uk.co.thecookingpot.oauth.service.ClientService
import uk.co.thecookingpot.oauth.service.TokenService

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val authCodeRepository = AuthCodeRepository()
    val clientRepository = ClientRepository()
    val userRepository = UserRepository()

    val authorisationService = AuthorisationService(authCodeRepository)
    val tokenService = TokenService(authCodeRepository)
    val clientService = ClientService(clientRepository)
    val authenticationService = AuthenticationService(userRepository)

    install(ContentNegotiation)
    install(Sessions) {
        configureAuthCookie()
        configureOriginCookie()
    }
    install(Authentication) {
        configureSessionAuth()
        configureFormAuth(authenticationService)
    }
    install(Routing) {
        home()
        login()
        authorise(authorisationService, clientService)
        token(tokenService)
    }
}

