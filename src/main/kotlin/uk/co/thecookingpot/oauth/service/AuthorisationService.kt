package uk.co.thecookingpot.oauth.service

import uk.co.thecookingpot.oauth.model.AuthCode
import uk.co.thecookingpot.oauth.model.AuthRequest
import uk.co.thecookingpot.login.repository.User
import uk.co.thecookingpot.oauth.model.Client
import uk.co.thecookingpot.oauth.model.Session
import uk.co.thecookingpot.oauth.repository.SessionRepository
import uk.co.thecookingpot.oauth.utility.createTimestampInFuture
import uk.co.thecookingpot.oauth.utility.generateAuthCode

class AuthorisationService(
    private val sessionRepository: SessionRepository
) {
    fun createAuthCode(client: Client, user: User, authRequest: AuthRequest): AuthCode  {
        if (authRequest.responseType != "code") {
            // TODO validation moved to validator pattern
        }

        AuthCode().apply {
            code = generateAuthCode()
            expires = createTimestampInFuture(60)
        }.let {
            sessionRepository.save(Session().apply {
                this.client = client
                this.user = user
                authCode = it
                nonce = authRequest.nonce
            })
            return it
        }
    }
}