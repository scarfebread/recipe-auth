package uk.co.thecookingpot.oauth.routes.validation

import uk.co.thecookingpot.oauth.model.AuthRequest
import uk.co.thecookingpot.oauth.model.Client
import uk.co.thecookingpot.oauth.model.Session
import uk.co.thecookingpot.oauth.model.TokenRequest

class ValidationResponse {
    lateinit var session: Session
    lateinit var client: Client
    lateinit var tokenRequest: TokenRequest
    lateinit var authRequest: AuthRequest
    var failure: ValidationFailure? = null
}