package uk.co.thecookingpot.oauth.routes.validation.token

import io.ktor.http.*
import uk.co.thecookingpot.oauth.config.INVALID_GRANT
import uk.co.thecookingpot.oauth.model.Client
import uk.co.thecookingpot.oauth.repository.SessionRepository

class InvalidGrantValidator(private val sessionRepository: SessionRepository, private val client: Client): TokenValidator {
    override fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean {
        val session = sessionRepository.findByAuthCode(validationResponse.tokenRequest.code)

        if (
            session == null ||
            session.client != client ||
            session.authCode!!.redirectUri != validationResponse.tokenRequest.redirectUri
        ) {
            validationResponse.failure = ValidationFailure(INVALID_GRANT, "Invalid authorization_code")
            return false
        }

        validationResponse.session = session
        return true
    }
}