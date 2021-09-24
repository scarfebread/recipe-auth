package uk.co.thecookingpot.oauth.utility

fun generateToken(): String {
    return generateString(20)
}

private const val CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"

private fun generateString(length: Int): String {
    return (1..length).map { CHAR_SET.random() }.joinToString("")
}