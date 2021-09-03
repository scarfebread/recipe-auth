package uk.co.thecookingpot.login

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import uk.co.thecookingpot.login.session.UserPrincipal
import uk.co.thecookingpot.login.exception.InvalidCredentialsException
import uk.co.thecookingpot.login.session.ClientPrincipal
import uk.co.thecookingpot.oauth.repository.ClientRepository

fun Authentication.Configuration.configureFormAuth(authenticationService: AuthenticationService) {
    form("loginForm") {
        userParamName = "username"
        passwordParamName = "password"
        challenge {
            call.respondRedirect("/login?error")
        }
        validate { credentials: UserPasswordCredential ->
            try {
                UserPrincipal(
                    authenticationService.authenticate(credentials.name, credentials.password)
                )
            } catch (e: InvalidCredentialsException) {
                null
            }
        }
    }
}

fun Authentication.Configuration.configureSessionAuth() {
    session<UserPrincipal> {
        challenge {
            call.response.cookies.append("redirect-uri", call.request.uri)
            call.respondRedirect("/login")
        }
        validate { session: UserPrincipal ->
            session
        }
    }
}

fun Authentication.Configuration.configureClientCredentialsAuth(clientRepository: ClientRepository) {
    basic("clientCredentials") {
        validate { credentials: UserPasswordCredential ->
            val client = clientRepository.getClientByClientId(credentials.name)

            if (client != null && client.clientSecret == credentials.password) {
                ClientPrincipal(client)
            } else {
                null
            }
        }
    }
}