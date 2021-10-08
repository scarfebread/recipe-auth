package uk.co.thecookingpot.oauth.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import uk.co.thecookingpot.authentication.session.UserPrincipal
import uk.co.thecookingpot.oauth.exception.InvalidClientException
import uk.co.thecookingpot.oauth.model.AuthRequest
import uk.co.thecookingpot.oauth.service.AuthorisationService
import uk.co.thecookingpot.oauth.service.ClientService

fun Route.authorise(authorisationService: AuthorisationService, clientService: ClientService) {
    authenticate {
        get("/authorize") {
            val authRequest = AuthRequest.validate(call.request.queryParameters)

            val client = try {
                clientService.getClient(authRequest.clientId, authRequest.redirectUri)
            } catch (e: InvalidClientException) {
                // TODO what's the spec?
                throw e;
            }

            val authCode = authorisationService.createAuthCode(
                client,
                call.principal<UserPrincipal>()!!.user,
                authRequest,
                call.sessionId!!
            )

            call.respondRedirect("${authRequest.redirectUri}?code=${authCode.code}&state=${authRequest.state}")
        }
    }
}