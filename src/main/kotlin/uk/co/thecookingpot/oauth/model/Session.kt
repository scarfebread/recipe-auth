package uk.co.thecookingpot.oauth.model

class Session {
    var authCode: AuthCode? = null
    var token: Token? = null
    var nonce: String? = null
}