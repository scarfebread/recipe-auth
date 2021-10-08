package uk.co.thecookingpot.authentication.session

import uk.co.thecookingpot.caching.RedisClient

class UserSessionRepository(private val redisClient: RedisClient) {
    fun write(sessionId: String, value: String) {
        redisClient.write(PREFIX + sessionId, value, TTL_THIRTY_MINUTES)
    }

    fun read(sessionId: String): String {
        return redisClient.read(PREFIX + sessionId)
    }

    fun delete(sessionId: String) {
        redisClient.delete(PREFIX + sessionId)
    }

    companion object {
        private const val PREFIX = "user-session:"
        private const val TTL_THIRTY_MINUTES = 1800L
    }
}