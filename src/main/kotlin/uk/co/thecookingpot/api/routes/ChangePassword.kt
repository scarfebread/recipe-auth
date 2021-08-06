package uk.co.thecookingpot.api.routes

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import uk.co.thecookingpot.login.repository.UserRepository
import uk.co.thecookingpot.api.exception.InvalidUserException
import uk.co.thecookingpot.api.routes.request.ChangePasswordRequest
import uk.co.thecookingpot.api.service.BearerAuthenticationService

fun Route.changePassword(bearerAuthenticationService: BearerAuthenticationService, userRepository: UserRepository) {
    post("/api/change-password") {
        val session = bearerAuthenticationService.byAccessToken(call.request.headers)

        val request = call.receive<ChangePasswordRequest>()
        val username = request.username ?: throw MissingRequestParameterException("username")
        val password = request.password ?: throw MissingRequestParameterException("password")

        val user = userRepository.findByUsername(session.user!!.username)

        if (user!!.username != username) {
            throw InvalidUserException("Username did not match authenticated user")
        }

        user.password = password

        userRepository.save(user)

        call.respond(HttpStatusCode.Accepted, "Accepted")
    }
}