package uk.co.thecookingpot.oauth.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import uk.co.thecookingpot.authentication.session.ClientPrincipal
import uk.co.thecookingpot.oauth.repository.ClientRepository
import uk.co.thecookingpot.oauth.repository.SessionRepository
import uk.co.thecookingpot.oauth.routes.validation.token.*
import uk.co.thecookingpot.oauth.service.TokenService

fun Route.token(tokenService: TokenService, clientRepository: ClientRepository, sessionRepository: SessionRepository) {
    authenticate("clientCredentials") {
        post("/token") {
            val client = call.principal<ClientPrincipal>()!!.client
            val validationResponse = ValidationResponse()
            val parameters = call.receiveParameters()
            listOf(
                InvalidRequestValidator(),
                InvalidClientValidator(),
                UnsupportedGrantTypeValidator(),
                InvalidGrantValidator(sessionRepository, client),
                UnauthorisedClientValidator(client),
            ).forEach { validator ->
                val result = validator.validate(parameters, validationResponse)

                if (!result) {
                    val failure = validationResponse.failure!!
                    call.respond(failure)
                    println("[ERROR] ${failure.error} - ${failure.errorDescription}")
                    return@post
                }
            }

            call.respond(
                tokenService.generateToken(
                    validationResponse.session
                )
            )
        }
    }
}