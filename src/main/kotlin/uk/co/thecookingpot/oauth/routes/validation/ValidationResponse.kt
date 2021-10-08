package uk.co.thecookingpot.oauth.routes.validation

import uk.co.thecookingpot.oauth.model.Client
import uk.co.thecookingpot.oauth.model.Session

class ValidationResponse {
    lateinit var session: Session
    lateinit var client: Client
    var failure: ValidationFailure? = null
}