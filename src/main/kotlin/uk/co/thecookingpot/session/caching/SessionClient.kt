package uk.co.thecookingpot.session.caching

class SessionClient(private val redisClient: RedisClient) {
    fun write(key: String, value: String) {
        redisClient.write(PREFIX + key, value)
    }

    fun read(key: String): String {
        return redisClient.read(PREFIX + key)
    }

    fun delete(key: String) {
        redisClient.delete(PREFIX + key)
    }

    companion object {
        private const val PREFIX = "s:"
    }
}