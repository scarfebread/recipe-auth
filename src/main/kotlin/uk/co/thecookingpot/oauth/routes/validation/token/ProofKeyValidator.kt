package uk.co.thecookingpot.oauth.routes.validation.token

import io.ktor.http.*
import uk.co.thecookingpot.oauth.routes.validation.Validator
import uk.co.thecookingpot.oauth.routes.validation.ValidationResponse

class ProofKeyValidator: Validator {
    override fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean {
        val session = validationResponse.session.authCode
        val request = validationResponse.tokenRequest

        return true
    }
}