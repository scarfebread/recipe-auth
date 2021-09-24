package uk.co.thecookingpot.oauth.model

import uk.co.thecookingpot.login.repository.User

// TODO what was the reason these are optional?
class Session {
    var sessionId: String? = null
    var client: Client? = null
    var user: User? = null
    var authCode: AuthCode? = null
    var token: Token? = null
    var nonce: String? = null
}