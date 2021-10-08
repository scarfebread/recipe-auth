package uk.co.thecookingpot.authentication.session

import uk.co.thecookingpot.oauth.model.Client
import io.ktor.auth.*

class ClientPrincipal(val client: Client): Principal