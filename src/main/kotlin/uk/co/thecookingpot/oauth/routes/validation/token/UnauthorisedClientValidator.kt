package uk.co.thecookingpot.oauth.routes.validation.token

import io.ktor.http.*
import uk.co.thecookingpot.oauth.config.UNAUTHORIZED_CLIENT
import uk.co.thecookingpot.oauth.model.Client

class UnauthorisedClientValidator(private val client: Client): TokenValidator {
    override fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean {
        if (!client.grantTypes.contains(validationResponse.tokenRequest.grantType)) {
            validationResponse.failure = ValidationFailure(UNAUTHORIZED_CLIENT, "Unsupported grant type")
            return false
        }

        return true
    }
}