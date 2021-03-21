package uk.co.thecookingpot.authentication

import io.ktor.auth.*
import uk.co.thecookingpot.model.User

class AuthPrinciple(val user: User): Principal