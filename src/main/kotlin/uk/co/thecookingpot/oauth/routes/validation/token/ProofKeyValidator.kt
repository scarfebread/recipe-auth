package uk.co.thecookingpot.oauth.routes.validation.token

import io.ktor.http.*

class ProofKeyValidator: TokenValidator {
    override fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean {
        val session = validationResponse.session.authCode
        val request = validationResponse.tokenRequest

        return true
    }
}