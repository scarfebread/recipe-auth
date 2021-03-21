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
import uk.co.thecookingpot.routes.authorise
import uk.co.thecookingpot.routes.login
import uk.co.thecookingpot.service.AuthorisationService
import uk.co.thecookingpot.service.ClientService

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation)
    install(Sessions) {
        configureAuthCookie()
        configureOriginCookie()
    }
    install(Authentication) {
        configureSessionAuth()
        configureFormAuth()
    }

    routing {
        login()
        authorise(
            AuthorisationService(
                ClientRepository(),
                AuthCodeRepository()
            ),
            ClientService(
                ClientRepository()
            )
        )
    }
}

