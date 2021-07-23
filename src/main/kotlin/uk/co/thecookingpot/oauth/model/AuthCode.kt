package uk.co.thecookingpot.oauth.model

import uk.co.thecookingpot.login.repository.User

class AuthCode {
    lateinit var code: String
    lateinit var user: User
    lateinit var expires: Number

    fun isExpired(): Boolean {
        return System.currentTimeMillis() > expires.toLong()
    }
}