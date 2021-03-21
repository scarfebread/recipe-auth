package uk.co.thecookingpot.repository

import uk.co.thecookingpot.model.User

class UserRepository {
    private val users = listOf(User().apply {
        username = "jscarfe"
        password = "12345"
        email = "james.scarfe@live.co.uk"
    })

    fun findByUsername(username: String): User? {
        return users.find { user ->
            user.username == username
        }
    }
}