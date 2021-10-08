package uk.co.thecookingpot.oauth.routes.validation.token

import io.ktor.http.*
import uk.co.thecookingpot.oauth.config.CODE
import uk.co.thecookingpot.oauth.config.INVALID_GRANT
import uk.co.thecookingpot.oauth.config.REDIRECT_URI
import uk.co.thecookingpot.oauth.model.Client
import uk.co.thecookingpot.oauth.repository.SessionRepository
import uk.co.thecookingpot.oauth.routes.validation.Validator
import uk.co.thecookingpot.oauth.routes.validation.ValidationFailure
import uk.co.thecookingpot.oauth.routes.validation.ValidationResponse

class InvalidGrantValidator(private val sessionRepository: SessionRepository, private val client: Client):
    Validator {
    override fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean {
        val session = sessionRepository.findByAuthCode(parameters[CODE]!!)

        if (
            session == null ||
            session.client != client ||
            session.authCode!!.redirectUri != parameters[REDIRECT_URI]
        ) {
            validationResponse.failure = ValidationFailure(INVALID_GRANT, "Invalid authorization_code")
            return false
        }

        validationResponse.session = session
        return true
    }
}