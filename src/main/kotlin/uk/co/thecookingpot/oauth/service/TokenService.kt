package uk.co.thecookingpot.oauth.service

import uk.co.thecookingpot.oauth.model.Session
import uk.co.thecookingpot.oauth.model.Token
import uk.co.thecookingpot.oauth.repository.SessionRepository
import uk.co.thecookingpot.oauth.service.utility.generateToken

class TokenService(private val sessionRepository: SessionRepository, private val jwtService: JwtService) {
    fun generateToken(session: Session): Token {
        session.token = Token().apply {
            accessToken = generateToken()
            refreshToken = generateToken()
            tokenType = "bearer"
            idToken = jwtService.createIdToken(session)
        }

        sessionRepository.saveTokenSet(session)

        return session.token!!
    }
}