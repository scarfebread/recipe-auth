package uk.co.thecookingpot.oauth.routes.validation.token

import io.ktor.http.*
import uk.co.thecookingpot.oauth.routes.validation.Validator
import uk.co.thecookingpot.oauth.routes.validation.ValidationResponse

class InvalidClientValidator: Validator {
    override fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean {
        // This validation is completed as part of the authentication config
        return true;
    }
}