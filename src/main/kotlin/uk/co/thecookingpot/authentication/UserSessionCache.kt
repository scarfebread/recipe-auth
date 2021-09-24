package uk.co.thecookingpot.authentication

import io.ktor.sessions.*
import io.ktor.utils.io.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.ByteArrayOutputStream
import kotlin.coroutines.coroutineContext

class UserSessionCache(private val sessionClient: UserSessionRepository): SessionStorage {
    override suspend fun invalidate(id: String) {
        sessionClient.delete(id)
    }

    override suspend fun <R> read(id: String, consumer: suspend (ByteReadChannel) -> R): R {
        return consumer(ByteReadChannel(sessionClient.read(id)))
    }

    override suspend fun write(id: String, provider: suspend (ByteWriteChannel) -> Unit) {
        provider(CoroutineScope(Dispatchers.IO).reader(coroutineContext, autoFlush = true) {
            sessionClient.write(id, channel.readAvailable())
        }.channel)
    }

    private suspend fun ByteReadChannel.readAvailable(): String {
        val data = ByteArrayOutputStream()
        val temp = ByteArray(1024)
        while (!isClosedForRead) {
            val read = readAvailable(temp)
            if (read <= 0) break
            data.write(temp, 0, read)
        }
        return data.toString()
    }
}