package uk.co.thecookingpot.repository

import uk.co.thecookingpot.model.Session

class SessionRepository {
    private val sessions = mutableListOf<Session>()

    fun save(session: Session) {
        sessions.add(session)
    }

    fun findByAuthCode(authCode: String) {
        sessions.first { session ->
            session.authCode?.code == authCode && !session.authCode!!.isExpired()
        }
    }
}