package uk.co.thecookingpot.oauth.model

class AuthCode {
    lateinit var code: String
    lateinit var expires: Number

    fun isExpired(): Boolean {
        return System.currentTimeMillis() > expires.toLong()
    }
}