package uk.co.thecookingpot.service

import uk.co.thecookingpot.model.AuthCode
import uk.co.thecookingpot.model.AuthRequest
import uk.co.thecookingpot.model.User
import uk.co.thecookingpot.repository.AuthCodeRepository
import uk.co.thecookingpot.utility.createTimestampInFuture
import uk.co.thecookingpot.utility.generateAuthCode

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