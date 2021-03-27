package uk.co.thecookingpot.oauth.service

import uk.co.thecookingpot.oauth.exception.InvalidClientException
import uk.co.thecookingpot.oauth.model.Client
import uk.co.thecookingpot.oauth.repository.ClientRepository

class ClientService(private val clientRepository: ClientRepository) {
    fun getClient(clientId: String, redirectUri: String): Client {
        return clientRepository.getClientByClientId(clientId)?.also { client ->
            if (!client.redirectUris.contains(redirectUri)) {
                throw InvalidClientException("Invalid redirect URI $redirectUri")
            }
        } ?: throw InvalidClientException("Client $clientId does not exist")
    }
}