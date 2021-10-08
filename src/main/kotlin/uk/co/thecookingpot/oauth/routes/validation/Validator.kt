package uk.co.thecookingpot.oauth.routes.validation

import io.ktor.http.*

interface Validator {
    fun validate(parameters: Parameters, validationResponse: ValidationResponse): Boolean
}