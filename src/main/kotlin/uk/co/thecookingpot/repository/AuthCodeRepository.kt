package uk.co.thecookingpot.repository

import uk.co.thecookingpot.model.AuthCode
import uk.co.thecookingpot.model.User

class AuthCodeRepository {
    private val accessTokens = ArrayList<AuthCode>()

    fun save(accessToken: AuthCode) {
        accessTokens.add(accessToken)
    }

    fun findByUser(user: User): AuthCode {
        return accessTokens.first { accessToken ->
            accessToken.user == user
        }
    }
}