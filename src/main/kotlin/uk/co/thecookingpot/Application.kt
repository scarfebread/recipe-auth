package uk.co.thecookingpot

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.sessions.*
import uk.co.thecookingpot.authentication.configureAuthCookie
import uk.co.thecookingpot.authentication.configureFormAuth
import uk.co.thecookingpot.authentication.configureOriginCookie
import uk.co.thecookingpot.authentication.configureSessionAuth
import uk.co.thecookingpot.repository.AuthCodeRepository
import uk.co.thecookingpot.repository.ClientRepository
import uk.co.thecookingpot.repository.UserRepository
import uk.co.thecookingpot.routes.authorise
import uk.co.thecookingpot.routes.home
import uk.co.thecookingpot.routes.login
import uk.co.thecookingpot.routes.token
import uk.co.thecookingpot.service.AuthenticationService
import uk.co.thecookingpot.service.AuthorisationService
import uk.co.thecookingpot.service.ClientService
import uk.co.thecookingpot.service.TokenService

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

