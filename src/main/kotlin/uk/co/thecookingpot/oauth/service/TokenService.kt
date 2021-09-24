package uk.co.thecookingpot.oauth.service

import uk.co.thecookingpot.oauth.exception.InvalidAuthCodeException
import uk.co.thecookingpot.oauth.exception.InvalidRefreshTokenException
import uk.co.thecookingpot.oauth.model.Token
import uk.co.thecookingpot.oauth.model.TokenRequest
import uk.co.thecookingpot.oauth.repository.SessionRepository
import uk.co.thecookingpot.oauth.service.utility.generateToken

class TokenService(private val sessionRepository: SessionRepository, private val jwtService: JwtService) {
    fun generateToken(request: TokenRequest): Token {
        val session = if (request.grantType == "authorization_code") {
            sessionRepository.findByAuthCode(request.code) ?: throw InvalidAuthCodeException("Unable to find ${request.code}")
        } else {
            sessionRepository.findByRefreshToken(request.code) ?: throw InvalidRefreshTokenException("Unable to find ${request.code}")
        }

        session.token = Token().apply {
            access_token = generateToken()
            refresh_token = generateToken()
            token_type = "bearer" // TODO bearer?
            id_token = jwtService.createIdToken(session)
        }

        sessionRepository.saveTokenSet(session)

        return session.token!!
    }
}