package uk.co.thecookingpot.oauth.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import uk.co.thecookingpot.oauth.repository.SessionRepository
import uk.co.thecookingpot.oauth.routes.request.RevokeRequest

fun Route.revoke(sessionRepository: SessionRepository) {
    authenticate("clientCredentials") {
        post("/revoke") {
            val request = call.receive<RevokeRequest>()

            val token = request.token ?: throw MissingRequestParameterException("token")
            val tokenTypeHint = request.token_type_hint

            val session = sessionRepository.findByToken(token, tokenTypeHint)

            session?.run {
                sessionRepository.deleteBySession(session)
            }

            call.respond(HttpStatusCode.OK, "OK")
        }
    }
}