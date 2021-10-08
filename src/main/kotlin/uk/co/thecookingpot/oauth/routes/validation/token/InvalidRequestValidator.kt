package uk.co.thecookingpot.oauth.routes.validation.token

import io.ktor.http.*
import uk.co.thecookingpot.oauth.config.CODE
import uk.co.thecookingpot.oauth.config.GRANT_TYPE
import uk.co.thecookingpot.oauth.config.INVALID_REQUEST
import uk.co.thecookingpot.oauth.config.REDIRECT_URI
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

        return true;
    }

    companion object {
        private val mandatoryParameters = listOf(
            CODE,
            GRANT_TYPE,
            REDIRECT_URI
        )
    }
}