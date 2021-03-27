package uk.co.thecookingpot.login.session

import io.ktor.auth.*
import uk.co.thecookingpot.login.repository.User

class AuthPrinciple(val user: User): Principal