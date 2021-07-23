package uk.co.thecookingpot.oauth.repository

import uk.co.thecookingpot.oauth.model.Client

// TODO database
class ClientRepository {
    private val clients = listOf(Client().apply {
        clientId = "recipe-application"
//        redirectUris = listOf("http://localhost:8080/oauth/redirect")
        redirectUris = listOf("http://localhost:8080/login/oauth2/code/recipe-auth")
        clientSecret = "recipe-application"
        publicClient = false
    })

    fun getClientByClientId(clientId: String): Client? {
        return clients.find { client ->
            client.clientId == clientId
        }
    }
}