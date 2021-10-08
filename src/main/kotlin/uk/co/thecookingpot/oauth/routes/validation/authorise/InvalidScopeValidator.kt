package uk.co.thecookingpot.oauth.routes.validation.authorise

import io.ktor.http.*
import uk.co.thecookingpot.oauth.config.INVALID_SCOPE
import uk.co.thecookingpot.oauth.config.SCOPE
import uk.co.thecookingpot.oauth.routes.validation.ValidationFailure
import uk.co.thecookingpot.oauth.routes.validation.ValidationResponse
import uk.co.thecookingpot.oauth.routes.validation.Validator

// TODO this seems wrong
class InvalidScopeValidator: Validator {
    override fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean {
        validationResponse.client.scopes.forEach { scope ->
            if (!parameters[SCOPE].toString().contains(scope)) {
                validationResponse.failure = ValidationFailure(INVALID_SCOPE, "Client scopes not fulfilled")
                return false
            }
        }

        return true;
    }
}