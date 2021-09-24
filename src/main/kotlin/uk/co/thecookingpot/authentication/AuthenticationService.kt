package uk.co.thecookingpot.authentication

import uk.co.thecookingpot.login.exception.InvalidCredentialsException
import uk.co.thecookingpot.login.repository.User
import uk.co.thecookingpot.login.repository.UserRepository

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