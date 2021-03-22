package uk.co.thecookingpot.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import uk.co.thecookingpot.authentication.AuthPrinciple
import uk.co.thecookingpot.exception.InvalidClientException
import uk.co.thecookingpot.model.AuthRequest
import uk.co.thecookingpot.service.AuthorisationService
import uk.co.thecookingpot.service.ClientService

fun Route.authorise(authorisationService: AuthorisationService, clientService: ClientService) {
    authenticate {
        get("/authorize") {
            val authRequest = AuthRequest.validate(call.request.queryParameters)

            try {
                clientService.getClient(authRequest.clientId, authRequest.redirectUri)
            } catch (e: InvalidClientException) {
                // TODO what's the spec?
                throw e;
            }

            val authCode = authorisationService.createAuthCode(
                call.principal<AuthPrinciple>()!!.user,
                authRequest
            )

            call.respondRedirect("${authRequest.redirectUri}?code=${authCode.code}&state=${authRequest.state}")
        }
    }
}