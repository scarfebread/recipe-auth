package uk.co.thecookingpot.oauth.routes.validation.token

import io.ktor.http.*
import uk.co.thecookingpot.oauth.config.GRANT_TYPE
import uk.co.thecookingpot.oauth.config.UNAUTHORIZED_CLIENT
import uk.co.thecookingpot.oauth.model.Client
import uk.co.thecookingpot.oauth.routes.validation.Validator
import uk.co.thecookingpot.oauth.routes.validation.ValidationFailure
import uk.co.thecookingpot.oauth.routes.validation.ValidationResponse

class UnauthorisedClientValidator(private val client: Client): Validator {
    override fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean {
        if (!client.grantTypes.contains(parameters[GRANT_TYPE])) {
            validationResponse.failure = ValidationFailure(UNAUTHORIZED_CLIENT, "Unsupported grant type")
            return false
        }

        return true
    }
}