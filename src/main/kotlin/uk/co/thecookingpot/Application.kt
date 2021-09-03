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
import uk.co.thecookingpot.login.*
import uk.co.thecookingpot.oauth.repository.ClientRepository
import uk.co.thecookingpot.login.repository.UserRepository
import uk.co.thecookingpot.oauth.repository.SessionRepository
import uk.co.thecookingpot.oauth.routes.*
import uk.co.thecookingpot.oauth.service.AuthorisationService
import uk.co.thecookingpot.oauth.service.ClientService
import uk.co.thecookingpot.oauth.service.JwtService
import uk.co.thecookingpot.oauth.service.TokenService
import javax.sql.DataSource

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val clientRepository = ClientRepository()
    val userRepository = UserRepository(dataSource(environment.config))
    val sessionRepository = SessionRepository()

    val authorisationService = AuthorisationService(sessionRepository)
    val jwtService = JwtService()
    val tokenService = TokenService(sessionRepository, jwtService)
    val clientService = ClientService(clientRepository)
    val authenticationService = AuthenticationService(userRepository)

    install(ContentNegotiation) {
        gson()
    }
    install(Sessions) {
        configureAuthCookie()
        configureOriginCookie()
    }
    install(Authentication) {
        configureSessionAuth()
        configureFormAuth(authenticationService)
        configureClientCredentialsAuth(clientRepository)
    }
    install(Routing) {
        home()
        login()
        authorise(authorisationService, clientService)
        token(tokenService)
        wellKnown(jwtService)
        changePassword(sessionRepository, userRepository)
        revoke(sessionRepository)
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

