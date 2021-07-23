package uk.co.thecookingpot.oauth.model

class Token {
    lateinit var access_token: String
    lateinit var refresh_token: String
    lateinit var token_type: String
    lateinit var id_token: String
}