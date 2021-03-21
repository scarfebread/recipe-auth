package uk.co.thecookingpot.model

class AuthCode {
    lateinit var code: String
    lateinit var user: User
    lateinit var expires: String
}