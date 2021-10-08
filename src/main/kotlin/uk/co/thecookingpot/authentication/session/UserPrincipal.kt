package uk.co.thecookingpot.authentication.session

import io.ktor.auth.*
import uk.co.thecookingpot.login.repository.User

class UserPrincipal(val user: User): Principal