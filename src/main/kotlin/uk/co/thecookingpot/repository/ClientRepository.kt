package uk.co.thecookingpot.repository

import uk.co.thecookingpot.model.Client

class ClientRepository {
    private val clients = ArrayList<Client>().apply {
        add(Client().apply {
            clientId = "recipe-application"
            redirectUris = listOf("http://localhost:8080/auth/callback")
            clientSecret = "recipe-application"
            publicClient = false
        })
    }

    fun getClientByClientId(clientId: String): Client? {
        return clients.find { client ->
            client.clientId == clientId
        }
    }
}