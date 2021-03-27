package uk.co.thecookingpot.oauth.repository

import uk.co.thecookingpot.oauth.model.AuthCode
import uk.co.thecookingpot.login.repository.User

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

    fun findByCode(code: String): AuthCode {
        return accessTokens.first { accessToken ->
            accessToken.code == code
        }
    }
}