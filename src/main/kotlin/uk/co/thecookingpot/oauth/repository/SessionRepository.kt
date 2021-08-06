package uk.co.thecookingpot.oauth.repository

import uk.co.thecookingpot.oauth.model.Session

// TODO caching solution
class SessionRepository {
    private val sessions = mutableListOf<Session>()

    fun save(session: Session) {
        sessions.add(session)
    }

    fun findByAuthCode(authCode: String): Session? {
        return sessions.find { session ->
            session.authCode?.code == authCode && !session.authCode!!.isExpired()
        }
    }

    fun findByRefreshToken(refreshToken: String): Session? {
        return sessions.find { session ->
            session.token?.refresh_token == refreshToken
            // TODO and is not expired
        }
    }

    fun findByAccessToken(accessToken: String): Session? {
        return sessions.find { session ->
            session.token?.access_token == accessToken
            // TODO and is not expired
        }
    }
}