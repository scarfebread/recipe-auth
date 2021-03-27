package uk.co.thecookingpot.oauth.repository

import uk.co.thecookingpot.oauth.model.Session

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