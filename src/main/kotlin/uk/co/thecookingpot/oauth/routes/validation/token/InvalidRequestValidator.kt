package uk.co.thecookingpot.oauth.routes.validation.token

import io.ktor.application.*
import io.ktor.http.*
import uk.co.thecookingpot.oauth.config.INVALID_REQUEST
import uk.co.thecookingpot.oauth.model.TokenRequest

class InvalidRequestValidator: TokenValidator {
    override fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean {
        mandatoryParameters.forEach { parameter ->
            if (!parameters.contains(parameter)) {
                validationResponse.failure = ValidationFailure(INVALID_REQUEST, "Missing mandatory parameter: $parameter")
                return false
            }
        }

        validationResponse.tokenRequest = TokenRequest.fromParameters(parameters)

        return true;
    }

    companion object {
        private val mandatoryParameters = listOf(
            "code",
            "grant_type",
            "redirect_uri",
            // TODO "code_verifier"
        )
    }
}