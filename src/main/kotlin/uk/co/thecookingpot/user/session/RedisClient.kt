package uk.co.thecookingpot.user.session

import redis.clients.jedis.Jedis
import redis.clients.jedis.params.SetParams

class RedisClient(host: String, port: Int, password: String) {
    private val redis: Jedis

    init {
        redis = Jedis(host, port).apply {
            this.auth(password)
        }
    }

    fun write(key: String, value: String, ttl: Long) {
        redis.set(key, value, SetParams().apply { ex (ttl) })
        redis[key] = value
    }

    fun read(key: String): String {
        return redis[key] ?: throw NoSuchElementException()
    }

    fun delete(key: String) {
        redis.del(key)
    }
}