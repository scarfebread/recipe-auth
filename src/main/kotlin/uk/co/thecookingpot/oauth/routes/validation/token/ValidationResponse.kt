package uk.co.thecookingpot.oauth.routes.validation.token

import uk.co.thecookingpot.oauth.model.Session
import uk.co.thecookingpot.oauth.model.TokenRequest

class ValidationResponse {
    lateinit var session: Session
    lateinit var tokenRequest: TokenRequest
    var failure: ValidationFailure? = null
}