package uk.co.thecookingpot.oauth.routes.validation.token

import io.ktor.http.*
import uk.co.thecookingpot.oauth.config.AUTHORISATION_CODE
import uk.co.thecookingpot.oauth.config.GRANT_TYPE
import uk.co.thecookingpot.oauth.config.UNSUPPORTED_GRANT_TYPE
import uk.co.thecookingpot.oauth.routes.validation.Validator
import uk.co.thecookingpot.oauth.routes.validation.ValidationFailure
import uk.co.thecookingpot.oauth.routes.validation.ValidationResponse

class UnsupportedGrantTypeValidator: Validator {
    override fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean {
        if (!VALID_GRANT_TYPES.contains(parameters[GRANT_TYPE])) {
            validationResponse.failure = ValidationFailure(UNSUPPORTED_GRANT_TYPE, "Unsupported grant type")
            return false
        }

        return true;
    }

    companion object {
        private val VALID_GRANT_TYPES = listOf(AUTHORISATION_CODE)
    }
}