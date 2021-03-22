package uk.co.thecookingpot.model

class AuthCode {
    lateinit var code: String
    lateinit var user: User
    lateinit var expires: Number
    lateinit var codeChallenge: String
    lateinit var codeChallengeMethod: String
}