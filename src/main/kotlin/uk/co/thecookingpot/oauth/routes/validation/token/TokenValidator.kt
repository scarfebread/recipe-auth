package uk.co.thecookingpot.oauth.routes.validation.token

import io.ktor.http.*

interface TokenValidator {
    fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean
}