package uk.co.thecookingpot.oauth.model

import io.ktor.features.*
import io.ktor.http.*

class TokenRequest {
    lateinit var clientId: String
    lateinit var redirectUri: String
    lateinit var grantType: String
    lateinit var clientSecret: String
    lateinit var codeVerifier: String
    lateinit var code: String

    companion object{
        private val mandatoryParameters = listOf(
            "client_id",
            "grant_type",
            "redirect_uri",
            "client_secret",
            "code_verifier",
            "code",
        )

        fun validate(parameters: Parameters): TokenRequest {
            mandatoryParameters.forEach { parameter ->
                if (!parameters.contains(parameter)) {
                    throw MissingRequestParameterException(parameter)
                }
            }

            return TokenRequest().apply {
                clientId = parameters["client_id"].toString()
                redirectUri = parameters["redirect_uri"].toString()
                grantType = parameters["grant_type"].toString()
                clientSecret = parameters["client_secret"].toString()
                codeVerifier = parameters["code_verifier"].toString()
                code = parameters["code"].toString()
            }
        }
    }
}