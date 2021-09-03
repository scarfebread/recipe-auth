package uk.co.thecookingpot.session.caching

import redis.clients.jedis.Jedis
import redis.clients.jedis.params.SetParams

class RedisClient(host: String, port: Int, password: String) {
    private val redis: Jedis

    init {
        redis = Jedis(host, port).apply {
            this.auth(password)
        }
    }

    fun write(key: String, value: String) {
        redis.set(key, value, CONFIG)
        redis[key] = value
    }

    fun read(key: String): String {
        return redis[key]
    }

    fun delete(key: String) {
        redis.del(key)
    }

    companion object {
        private const val TTL = (30 * 60000).toLong()
        private val CONFIG = SetParams().apply { ex (TTL) }
    }
}