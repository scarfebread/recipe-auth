package uk.co.thecookingpot.api.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import uk.co.thecookingpot.login.repository.UserRepository
import uk.co.thecookingpot.api.exception.InvalidUserException
import uk.co.thecookingpot.api.routes.request.ChangePasswordRequest
import uk.co.thecookingpot.login.session.ClientPrincipal
import uk.co.thecookingpot.login.session.UserPrincipal
import uk.co.thecookingpot.oauth.exception.InvalidAccessTokenException
import uk.co.thecookingpot.oauth.exception.InvalidClientException
import uk.co.thecookingpot.oauth.repository.SessionRepository

fun Route.changePassword(sessionRepository: SessionRepository, userRepository: UserRepository) {
    authenticate("clientCredentials") {
        post("/api/change-password") {
            val request = call.receive<ChangePasswordRequest>()
            val username = request.username ?: throw MissingRequestParameterException("username")
            val password = request.password ?: throw MissingRequestParameterException("password")
            val accessToken = request.accessToken ?: throw MissingRequestParameterException("accessToken")

            val session = sessionRepository.findByAccessToken(accessToken) ?: throw InvalidAccessTokenException("Invalid access token")
            val user = userRepository.findByUsername(session.user.username)
            val client = call.principal<ClientPrincipal>()!!.client

            if (client.clientId != session.client.clientId) {
                throw InvalidAccessTokenException("Invalid access token")
            }

            if (user!!.username != username) {
                throw InvalidUserException("Username did not match authenticated user")
            }

            user.password = password

            userRepository.save(user)

            call.respond(HttpStatusCode.Accepted, "Accepted")
        }
    }
}