package uk.co.thecookingpot.oauth.model

import uk.co.thecookingpot.login.repository.User

class Session {
    lateinit var sessionId: String
    lateinit var client: Client
    lateinit var user: User
    lateinit var nonce: String // TODO validate
    var authCode: AuthCode? = null
    var token: Token? = null
}