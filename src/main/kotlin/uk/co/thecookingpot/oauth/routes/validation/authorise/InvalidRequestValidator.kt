package uk.co.thecookingpot.oauth.routes.validation.authorise

import io.ktor.http.*
import uk.co.thecookingpot.oauth.config.*
import uk.co.thecookingpot.oauth.model.AuthRequest
import uk.co.thecookingpot.oauth.routes.validation.Validator
import uk.co.thecookingpot.oauth.routes.validation.ValidationFailure
import uk.co.thecookingpot.oauth.routes.validation.ValidationResponse

class InvalidRequestValidator: Validator {
    override fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean {
        mandatoryParameters.forEach { parameter ->
            if (!parameters.contains(parameter)) {
                validationResponse.failure = ValidationFailure(INVALID_REQUEST, "Missing mandatory parameter: $parameter")
                return false
            }
        }

        validationResponse.authRequest = AuthRequest.fromRequest(parameters)

        return true;
    }

    companion object {
        private val mandatoryParameters = listOf(
            CLIENT_ID,
            REDIRECT_URI,
            STATE,
            RESPONSE_TYPE,
            SCOPE,
        )
    }
}