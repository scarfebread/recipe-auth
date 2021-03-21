package uk.co.thecookingpot.service

import uk.co.thecookingpot.model.AuthCode
import uk.co.thecookingpot.model.AuthRequest
import uk.co.thecookingpot.repository.AuthCodeRepository
import uk.co.thecookingpot.repository.ClientRepository

class AuthorisationService(
    private val clientRepository: ClientRepository,
    private val authCodeRepository: AuthCodeRepository
) {
    fun handle(authRequest: AuthRequest): AuthCode  {
        val client = clientRepository.getClientByClientId(authRequest.clientId)

        // validate client
        // generate code

        return AuthCode().apply {
            code = "auth code"
        }
    }
}