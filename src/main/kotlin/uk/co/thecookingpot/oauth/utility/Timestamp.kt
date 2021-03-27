package uk.co.thecookingpot.oauth.utility

fun createTimestampInFuture(seconds: Int): Number {
    return System.currentTimeMillis() + (seconds * 1000)
}
