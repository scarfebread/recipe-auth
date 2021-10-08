package uk.co.thecookingpot.oauth.routes.validation.authorise

import io.ktor.http.*
import uk.co.thecookingpot.oauth.config.AUTHORISATION_CODE
import uk.co.thecookingpot.oauth.config.UNSUPPORTED_GRANT_TYPE
import uk.co.thecookingpot.oauth.routes.validation.ValidationFailure
import uk.co.thecookingpot.oauth.routes.validation.ValidationResponse
import uk.co.thecookingpot.oauth.routes.validation.Validator

class UnsupportedResponseTypeValidator: Validator {
    override fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean {
        if (validationResponse.client.grantTypes.contains(AUTHORISATION_CODE)) {
            return true;
        }

        validationResponse.failure = ValidationFailure(UNSUPPORTED_GRANT_TYPE, "Client does not support the supplied grant type")
        return false
    }
}