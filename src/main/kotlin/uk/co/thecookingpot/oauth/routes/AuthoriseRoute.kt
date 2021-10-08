package uk.co.thecookingpot.oauth.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import uk.co.thecookingpot.authentication.session.UserPrincipal
import uk.co.thecookingpot.oauth.config.NONCE
import uk.co.thecookingpot.oauth.config.REDIRECT_URI
import uk.co.thecookingpot.oauth.config.STATE
import uk.co.thecookingpot.oauth.repository.ClientRepository
import uk.co.thecookingpot.oauth.routes.validation.ValidationResponse
import uk.co.thecookingpot.oauth.routes.validation.authorise.InvalidRequestValidator
import uk.co.thecookingpot.oauth.routes.validation.authorise.InvalidScopeValidator
import uk.co.thecookingpot.oauth.routes.validation.authorise.UnauthorisedClientValidator
import uk.co.thecookingpot.oauth.routes.validation.authorise.UnsupportedResponseTypeValidator
import uk.co.thecookingpot.oauth.service.AuthorisationService

fun Route.authorise(authorisationService: AuthorisationService, clientRepository: ClientRepository) {
    authenticate {
        get("/authorize") {
            val validationResponse = ValidationResponse()
            val parameters = call.request.queryParameters

            listOf(
                InvalidRequestValidator(),
                UnauthorisedClientValidator(clientRepository),
                UnsupportedResponseTypeValidator(),
                InvalidScopeValidator()
            ).forEach { validator ->
                val result = validator.validate(parameters, validationResponse)

                if (!result) {
                    val failure = validationResponse.failure!!

                    if (parameters[REDIRECT_URI] != null) {
                        call.respondRedirect("${parameters[REDIRECT_URI]}?error=${failure.error}&error_description=${failure.errorDescription}")
                    } else {
                        call.respond(BadRequest, "${failure.error} - ${failure.errorDescription}")
                    }

                    println("[ERROR] /authorize ${failure.error} - ${failure.errorDescription}")
                    return@get
                }
            }

            val authCode = authorisationService.createAuthCode(
                validationResponse.client,
                call.principal<UserPrincipal>()!!.user,
                call.sessionId!!,
                parameters[REDIRECT_URI]!!,
                parameters[NONCE]!!
            )

            call.respondRedirect("${parameters[REDIRECT_URI]}?code=${authCode.code}&state=${parameters[STATE]}")
        }
    }
}