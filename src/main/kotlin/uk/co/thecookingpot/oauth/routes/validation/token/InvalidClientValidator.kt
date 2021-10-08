package uk.co.thecookingpot.oauth.routes.validation.token

import io.ktor.http.*

class InvalidClientValidator: TokenValidator {
    override fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean {
        // This validation is completed as part of the authentication config
        return true;
    }
}