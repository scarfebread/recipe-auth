package uk.co.thecookingpot.oauth.service

import uk.co.thecookingpot.oauth.exception.InvalidAuthCodeException
import uk.co.thecookingpot.oauth.exception.InvalidRefreshTokenException
import uk.co.thecookingpot.oauth.model.Session
import uk.co.thecookingpot.oauth.model.Token
import uk.co.thecookingpot.oauth.model.TokenRequest
import uk.co.thecookingpot.oauth.repository.SessionRepository
import uk.co.thecookingpot.oauth.routes.validation.token.InvalidRequestValidator
import uk.co.thecookingpot.oauth.service.utility.generateToken

class TokenService(private val sessionRepository: SessionRepository, private val jwtService: JwtService) {
    fun generateToken(session: Session): Token {
        session.token = Token().apply {
            access_token = generateToken()
            refresh_token = generateToken()
            token_type = "bearer"
            id_token = jwtService.createIdToken(session)
        }

        sessionRepository.saveTokenSet(session)

        return session.token!!
    }
}