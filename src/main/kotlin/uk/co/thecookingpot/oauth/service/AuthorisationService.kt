package uk.co.thecookingpot.oauth.service

import uk.co.thecookingpot.login.repository.User
import uk.co.thecookingpot.oauth.model.AuthCode
import uk.co.thecookingpot.oauth.model.Client
import uk.co.thecookingpot.oauth.model.Session
import uk.co.thecookingpot.oauth.repository.SessionRepository
import uk.co.thecookingpot.oauth.service.utility.generateToken

class AuthorisationService(private val sessionRepository: SessionRepository) {
    fun createAuthCode(client: Client, user: User, sessionId: String, redirectUri: String, nonce: String): AuthCode  {
        AuthCode().apply {
            code = generateToken()
            this.redirectUri = redirectUri
        }.let {
            sessionRepository.saveNewSession(Session().apply {
                this.sessionId = sessionId
                this.client = client
                this.user = user
                authCode = it
                this.nonce = nonce
            })
            return it
        }
    }
}