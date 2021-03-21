package uk.co.thecookingpot.model

import io.ktor.features.*
import io.ktor.http.*

class AuthRequest {
    lateinit var clientId: String
    lateinit var redirectUri: String
    lateinit var state: String
    lateinit var responseType: String
    lateinit var scope: String
    lateinit var codeChallenge: String
    lateinit var codeChallengeMethod: String

    companion object{
        private val mandatoryParameters = listOf(
            "client_id",
            "redirect_uri",
            "state",
            "response_type",
            "scope",
            "code_challenge",
            "code_challenge_method"
        )

        fun validate(parameters: Parameters): AuthRequest {
            mandatoryParameters.forEach { parameter ->
                if (!parameters.contains(parameter)) {
                    throw MissingRequestParameterException(parameter)
                }
            }

            return AuthRequest().apply {
                clientId = parameters["client_id"].toString()
                redirectUri = parameters["redirect_uri"].toString()
                state = parameters["state"].toString()
                scope = parameters["scope"].toString()
                responseType = parameters["response_type"].toString()
                codeChallenge = parameters["code_challenge"].toString()
                codeChallengeMethod = parameters["code_challenge_method"].toString()
            }
        }
    }
}