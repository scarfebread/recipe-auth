package uk.co.thecookingpot.oauth.service

import uk.co.thecookingpot.oauth.model.AuthCode
import uk.co.thecookingpot.oauth.model.AuthRequest
import uk.co.thecookingpot.login.repository.User
import uk.co.thecookingpot.oauth.repository.AuthCodeRepository
import uk.co.thecookingpot.oauth.utility.createTimestampInFuture
import uk.co.thecookingpot.oauth.utility.generateAuthCode

class AuthorisationService(
    private val authCodeRepository: AuthCodeRepository
) {
    fun createAuthCode(user: User, authRequest: AuthRequest): AuthCode  {
        if (authRequest.responseType != "code") {
            // TODO validation moved to validator pattern
        }

        AuthCode().apply {
            code = generateAuthCode()
            this.user = user
            expires = createTimestampInFuture(10)
            codeChallenge = authRequest.codeChallenge
            codeChallengeMethod = authRequest.codeChallengeMethod
        }.let { authCode ->
            authCodeRepository.save(authCode)
            return authCode
        }
    }
}