package uk.co.thecookingpot.oauth.service

import uk.co.thecookingpot.oauth.exception.InvalidAuthCodeException
import uk.co.thecookingpot.oauth.exception.InvalidRefreshTokenException
import uk.co.thecookingpot.oauth.model.Token
import uk.co.thecookingpot.oauth.model.TokenRequest
import uk.co.thecookingpot.oauth.repository.SessionRepository

class TokenService(private val sessionRepository: SessionRepository) {
    fun generateToken(request: TokenRequest): Token {
        val session = if (request.grantType == "authorization_code") {
            sessionRepository.findByAuthCode(request.code) ?: throw InvalidAuthCodeException("Unable to find ${request.code}")
        } else {
            sessionRepository.findByRefreshToken(request.code) ?: throw InvalidRefreshTokenException("Unable to find ${request.code}")
        }

        session.token = Token().apply {
            access_token = "accessToken"
            refresh_token = "refreshToken"
            token_type = "bearer"
            id_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE2MjcwNDY3NDcsImV4cCI6MTY1ODU4Mjc0NywiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianNjYXJmZSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjpbIk1hbmFnZXIiLCJQcm9qZWN0IEFkbWluaXN0cmF0b3IiXX0.CnZzi-co0Fb6evV4MyjpK9NgXPiwQj6BjihapMr51Ug"
        }

        return session.token!!
    }
}