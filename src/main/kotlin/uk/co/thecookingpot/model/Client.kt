package uk.co.thecookingpot.model

class Client {
    lateinit var clientId: String
    lateinit var redirectUris: List<String>
    lateinit var clientSecret: String
    var publicClient = false

}