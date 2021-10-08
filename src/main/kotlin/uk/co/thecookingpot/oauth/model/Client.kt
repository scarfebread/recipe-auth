package uk.co.thecookingpot.oauth.model

class Client {
    lateinit var clientId: String
    lateinit var redirectUris: List<String>
    lateinit var clientSecret: String
    lateinit var grantTypes: List<String>
    var publicClient = false

    override fun equals(other: Any?): Boolean {
        if (other !is Client) {
            return false
        }

        return this.clientId == other.clientId
    }

    override fun hashCode(): Int {
        var result = clientId.hashCode()
        result = 31 * result + redirectUris.hashCode()
        result = 31 * result + clientSecret.hashCode()
        result = 31 * result + publicClient.hashCode()
        return result
    }
}