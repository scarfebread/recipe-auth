package uk.co.thecookingpot.oauth.repository

import com.google.gson.Gson
import uk.co.thecookingpot.oauth.config.ACCESS_TOKEN
import uk.co.thecookingpot.oauth.config.REFRESH_TOKEN
import uk.co.thecookingpot.oauth.model.Session
import uk.co.thecookingpot.caching.RedisClient

class SessionRepository(private val redisClient: RedisClient) {
    private val gson = Gson()

    fun saveNewSession(session: Session) {
        redisClient.write(SESSION_PREFIX + session.sessionId, gson.toJson(session), SESSION_TTL)
        redisClient.write(AUTH_CODE_PREFIX + session.authCode!!.code, session.sessionId, AUTH_CODE_TTL)
    }

    fun saveTokenSet(session: Session) {
        redisClient.write(ACCESS_TOKEN_PREFIX + session.token!!.access_token, session.sessionId, ACCESS_TOKEN_TTL)
        redisClient.write(REFRESH_TOKEN_PREFIX + session.token!!.refresh_token, session.sessionId, REFRESH_TOKEN_TTL)
    }

    fun findByAuthCode(authCode: String): Session? {
        return findByKey(AUTH_CODE_PREFIX + authCode)?.run {
            redisClient.delete(AUTH_CODE_PREFIX + authCode)
            this
        }
    }

    fun findByRefreshToken(refreshToken: String): Session? {
        return findByKey(REFRESH_TOKEN_PREFIX + refreshToken)
    }

    fun findByAccessToken(accessToken: String): Session? {
        return findByKey(ACCESS_TOKEN_PREFIX + accessToken)
    }

    fun findByToken(token: String, tokenTypeHint: String?): Session? {
        return when (tokenTypeHint) {
            ACCESS_TOKEN -> findByAccessToken(token)
            REFRESH_TOKEN -> findByRefreshToken(token)
            else -> findByAccessToken(token) ?: findByRefreshToken(token)
        }
    }

    private fun findByKey(key: String): Session? {
        try {
            redisClient.read(key).run {
                redisClient.read(SESSION_PREFIX + this).run {
                    return gson.fromJson(this, Session::class.java)
                }
            }
        } catch (e: NoSuchElementException) {
            return null
        }
    }

    fun deleteBySession(session: Session) {
        redisClient.delete(SESSION_PREFIX + session.sessionId)

        session.authCode?.run {
            redisClient.delete(AUTH_CODE_PREFIX + session.authCode!!.code)
        }

        session.token?.run {
            redisClient.delete(ACCESS_TOKEN_PREFIX + session.token!!.access_token)
            redisClient.delete(REFRESH_TOKEN_PREFIX + session.token!!.refresh_token)
        }
    }

    companion object {
        private const val SESSION_PREFIX = "oauth-session:"
        private const val ACCESS_TOKEN_PREFIX = "access-token:"
        private const val REFRESH_TOKEN_PREFIX = "refresh-token:"
        private const val AUTH_CODE_PREFIX = "auth-token:"

        private const val SESSION_TTL = 14400L // 4 hours
        private const val AUTH_CODE_TTL = 60L // 1 minute
        private const val ACCESS_TOKEN_TTL = 3600L // 1 hour
        private const val REFRESH_TOKEN_TTL = 14400L // 4 hours
    }
}