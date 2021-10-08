package uk.co.thecookingpot.oauth.model

import io.ktor.http.*

class TokenRequest {
    lateinit var clientId: String
    lateinit var redirectUri: String
    lateinit var grantType: String
    lateinit var codeVerifier: String
    lateinit var code: String

    companion object{
        fun fromParameters(parameters: Parameters): TokenRequest {
            return TokenRequest().apply {
                clientId = parameters["client_id"].toString()
                redirectUri = parameters["redirect_uri"].toString()
                grantType = parameters["grant_type"].toString()
                codeVerifier = parameters["code_verifier"].toString()
                code = parameters["code"].toString()
            }
        }
    }
}