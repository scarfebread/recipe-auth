package uk.co.thecookingpot.repository

import uk.co.thecookingpot.exception.UserNotFoundException
import uk.co.thecookingpot.model.User

class UserRepository {
    private val users = ArrayList<User>()

    fun findByUsername(username: String): User {
        return users.find { user ->
            user.username == username
        } ?: throw UserNotFoundException("Unable to locate $username")
    }
}