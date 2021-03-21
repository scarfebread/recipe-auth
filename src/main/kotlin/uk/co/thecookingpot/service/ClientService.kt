package uk.co.thecookingpot.service

import uk.co.thecookingpot.exception.InvalidClientException
import uk.co.thecookingpot.model.Client
import uk.co.thecookingpot.repository.ClientRepository

class ClientService(private val clientRepository: ClientRepository) {
    fun getClient(clientId: String, redirectUri: String): Client {
        return clientRepository.getClientByClientId(clientId)?.also { client ->
            if (!client.redirectUris.contains(redirectUri)) {
                throw InvalidClientException("Invalid redirect URI $redirectUri")
            }
        } ?: throw InvalidClientException("Client $clientId does not exist")
    }
}