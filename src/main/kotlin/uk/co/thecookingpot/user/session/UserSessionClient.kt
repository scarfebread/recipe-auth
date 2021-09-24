package uk.co.thecookingpot.user.session

class UserSessionClient(private val redisClient: RedisClient) {
    fun write(key: String, value: String) {
        redisClient.write(PREFIX + key, value, TTL_THIRTY_MINUTES)
    }

    fun read(key: String): String {
        return redisClient.read(PREFIX + key)
    }

    fun delete(key: String) {
        redisClient.delete(PREFIX + key)
    }

    companion object {
        private const val PREFIX = "user-session:"
        private const val TTL_THIRTY_MINUTES = 1800L
    }
}