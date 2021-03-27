package uk.co.thecookingpot.oauth.service

import uk.co.thecookingpot.oauth.model.Token
import uk.co.thecookingpot.oauth.model.TokenRequest
import uk.co.thecookingpot.oauth.repository.AuthCodeRepository

class TokenService(private val authCodeRepository: AuthCodeRepository) {
    fun generateToken(request: TokenRequest): Token {
        if (request.grantType == "authorization_code") {
            authCodeRepository.findByCode(request.code).also { authCode ->

            }
        }



        return Token()
    }
}