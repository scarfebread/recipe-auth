package uk.co.thecookingpot.oauth.routes

import io.ktor.application.*
import io.ktor.routing.*
import uk.co.thecookingpot.oauth.model.TokenRequest
import uk.co.thecookingpot.oauth.service.TokenService

fun Route.token(tokenService: TokenService) {
    post("/token") {
        val request = TokenRequest.validate(call.request.queryParameters)

        tokenService.generateToken(request)
    }
}