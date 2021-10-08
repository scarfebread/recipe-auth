package uk.co.thecookingpot.oauth.repository

import uk.co.thecookingpot.oauth.config.AUTHORISATION_CODE
import uk.co.thecookingpot.oauth.config.OPENID
import uk.co.thecookingpot.oauth.model.Client

class ClientRepository {
    private val clients = listOf(Client().apply {
        clientId = "recipe-application"
        redirectUris = listOf("http://localhost:8080/login/oauth2/code/recipe-auth")
        clientSecret = "recipe-application"
        publicClient = false
        grantTypes = listOf(AUTHORISATION_CODE)
        scopes = listOf(OPENID)
    })

    fun getClientByClientId(clientId: String): Client? {
        return clients.find { client ->
            client.clientId == clientId
        }
    }
}