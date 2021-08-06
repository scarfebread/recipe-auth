package uk.co.thecookingpot.api.service

import io.ktor.http.*
import uk.co.thecookingpot.oauth.exception.InvalidAccessTokenException
import uk.co.thecookingpot.oauth.model.Session
import uk.co.thecookingpot.oauth.repository.SessionRepository

// TODO this should be handled by the auth plugin...
class BearerAuthenticationService(private val sessionRepository: SessionRepository) {
    fun byAccessToken(headers: Headers): Session {
        val accessToken = extractAccessTokenFromHeaders(headers)

        val session = if (accessToken != null) {
            sessionRepository.findByAccessToken(accessToken)
        } else {
            null
        }

        if (session == null) {
            throw InvalidAccessTokenException("Access token was missing or invalid")
        }

        return session
    }

    private fun extractAccessTokenFromHeaders(headers: Headers): String? {
        return headers[HttpHeaders.Authorization]?.split("Bearer ")?.get(1)
    }
}