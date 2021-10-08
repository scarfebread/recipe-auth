package uk.co.thecookingpot.oauth.routes.validation.token

import uk.co.thecookingpot.oauth.model.Client
import uk.co.thecookingpot.oauth.model.Session
import uk.co.thecookingpot.oauth.model.TokenRequest

class ValidationSuccess {
    lateinit var client: Client
    lateinit var session: Session
    lateinit var tokenRequest: TokenRequest
}