package uk.co.thecookingpot.oauth.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import uk.co.thecookingpot.authentication.session.ClientPrincipal
import uk.co.thecookingpot.oauth.exception.InvalidAccessTokenException
import uk.co.thecookingpot.oauth.repository.SessionRepository
import uk.co.thecookingpot.oauth.routes.request.RevokeRequest
import uk.co.thecookingpot.authentication.session.UserSessionRepository

fun Route.revoke(sessionRepository: SessionRepository, userSessionRepository: UserSessionRepository) {
    authenticate("clientCredentials") {
        post("/revoke") {
            val request = call.receive<RevokeRequest>()
            val token = request.token ?: throw MissingRequestParameterException("token")
            val tokenTypeHint = request.token_type_hint

            val session = sessionRepository.findByToken(token, tokenTypeHint)
            val client = call.principal<ClientPrincipal>()!!.client

            if (session != null && client.clientId == session.client.clientId) {
                session.run {
                    sessionRepository.deleteBySession(session)
                    userSessionRepository.delete(session.sessionId)
                }
            }

            call.respond(HttpStatusCode.OK, "OK")
        }
    }
}