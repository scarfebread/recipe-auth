package uk.co.thecookingpot

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.config.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import io.ktor.sessions.*
import uk.co.thecookingpot.api.routes.changePassword
import uk.co.thecookingpot.api.routes.home
import uk.co.thecookingpot.authentication.AuthenticationService
import uk.co.thecookingpot.oauth.repository.ClientRepository
import uk.co.thecookingpot.login.repository.UserRepository
import uk.co.thecookingpot.oauth.repository.SessionRepository
import uk.co.thecookingpot.oauth.routes.*
import uk.co.thecookingpot.oauth.service.AuthorisationService
import uk.co.thecookingpot.oauth.service.JwtService
import uk.co.thecookingpot.oauth.service.TokenService
import uk.co.thecookingpot.caching.RedisClient
import uk.co.thecookingpot.authentication.session.UserSessionCache
import uk.co.thecookingpot.authentication.session.UserSessionRepository
import uk.co.thecookingpot.authentication.config.configureAuthCookie
import uk.co.thecookingpot.authentication.config.configureClientCredentialsAuth
import uk.co.thecookingpot.authentication.config.configureFormAuth
import uk.co.thecookingpot.authentication.config.configureSessionAuth
import javax.sql.DataSource

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val redisClient = RedisClient(
        environment.config.property("redis.host").getString(),
        environment.config.property("redis.port").getString().toInt(),
        environment.config.property("redis.password").getString(),
    )
    val clientRepository = ClientRepository()
    val userRepository = UserRepository(dataSource(environment.config))
    val sessionRepository = SessionRepository(redisClient)
    val userSessionRepository = UserSessionRepository(redisClient)

    val authorisationService = AuthorisationService(sessionRepository)
    val jwtService = JwtService()
    val tokenService = TokenService(sessionRepository, jwtService)
    val authenticationService = AuthenticationService(userRepository)
    val sessionCache = UserSessionCache(userSessionRepository)

    install(ContentNegotiation) {
        gson()
    }
    install(Sessions) {
        configureAuthCookie(sessionCache)
    }
    install(Authentication) {
        configureSessionAuth()
        configureFormAuth(authenticationService)
        configureClientCredentialsAuth(clientRepository)
    }
    install(Routing) {
        home()
        login()
        authorise(authorisationService, clientRepository)
        token(tokenService, clientRepository, sessionRepository)
        wellKnown(jwtService)
        changePassword(sessionRepository, userRepository)
        revoke(sessionRepository, userSessionRepository)
    }
}

private fun dataSource(config: ApplicationConfig): DataSource {
    return HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = config.property("db.jdbcUrl").getString()
            username = config.property("db.username").getString()
            password = config.property("db.password").getString()
            driverClassName = config.property("db.driverClassName").getString()
            schema = config.property("db.schema").getString()
        }
    )
}

