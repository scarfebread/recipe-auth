package uk.co.thecookingpot.service

import uk.co.thecookingpot.exception.InvalidCredentialsException
import uk.co.thecookingpot.model.User
import uk.co.thecookingpot.repository.UserRepository

class AuthenticationService(private val userRepository: UserRepository) {
    fun authenticate(username: String, password: String): User {
        userRepository.findByUsername(username)?.also { user ->
            if (password == user.password) {
                return user;
            }
        }

        throw InvalidCredentialsException("Invalid username and password combination")
    }
}