package uk.co.thecookingpot.service

import uk.co.thecookingpot.model.Token
import uk.co.thecookingpot.model.TokenRequest
import uk.co.thecookingpot.repository.AuthCodeRepository

class TokenService(private val authCodeRepository: AuthCodeRepository) {
    fun generateToken(request: TokenRequest): Token {
        if (request.grantType == "authorization_code") {
            authCodeRepository.findByCode(request.code).also { authCode ->

            }
        }



        return Token()
    }
}