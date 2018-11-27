package be.ucll.dirkfalls.utils

fun scale(x: Float, minGiven: Float, maxGiven: Float, minTo: Float = 0f, maxTo: Float = 1f): Float {
    return x * ((maxTo - minTo) / (maxGiven - minGiven))
}