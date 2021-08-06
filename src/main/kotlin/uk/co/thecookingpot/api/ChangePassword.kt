package uk.co.thecookingpot.api

import io.ktor.routing.*
import uk.co.thecookingpot.oauth.repository.SessionRepository

fun Route.changePassword(sessionRepository: SessionRepository) {
    post("/api/change-password") {

    }
}