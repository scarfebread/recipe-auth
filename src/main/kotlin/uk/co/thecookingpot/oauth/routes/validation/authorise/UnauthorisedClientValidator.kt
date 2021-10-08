package uk.co.thecookingpot.oauth.routes.validation.authorise

import io.ktor.http.*
import uk.co.thecookingpot.oauth.config.*
import uk.co.thecookingpot.oauth.repository.ClientRepository
import uk.co.thecookingpot.oauth.routes.validation.Validator
import uk.co.thecookingpot.oauth.routes.validation.ValidationFailure
import uk.co.thecookingpot.oauth.routes.validation.ValidationResponse

class UnauthorisedClientValidator(private val clientRepository: ClientRepository): Validator {
    override fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean {
        val clientId = parameters[CLIENT_ID].toString()
        val client = clientRepository.getClientByClientId(clientId)

        if (client == null) {
            validationResponse.failure = ValidationFailure(UNAUTHORIZED_CLIENT, "Unknown client ")
            return false
        }

        if (!client.redirectUris.contains(parameters[REDIRECT_URI])) {
            validationResponse.failure = ValidationFailure(UNAUTHORIZED_CLIENT, "Invalid redirect URI")
            return false
        }

        if (parameters[RESPONSE_TYPE] == CODE && !client.grantTypes.contains(AUTHORISATION_CODE)) {
            validationResponse.failure = ValidationFailure(UNAUTHORIZED_CLIENT, "Unsupported grant type")
            return false
        }

        validationResponse.client = client
        return true
    }
}