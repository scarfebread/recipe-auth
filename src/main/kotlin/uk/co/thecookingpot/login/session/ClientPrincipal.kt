package uk.co.thecookingpot.login.session

import uk.co.thecookingpot.oauth.model.Client
import io.ktor.auth.*

class ClientPrincipal(val client: Client): Principal