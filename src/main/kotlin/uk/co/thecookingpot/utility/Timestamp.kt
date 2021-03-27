package uk.co.thecookingpot.utility

fun createTimestampInFuture(seconds: Int): Number {
    return System.currentTimeMillis() + (seconds * 1000)
}
