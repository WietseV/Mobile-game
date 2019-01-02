package be.ucll.dirkfalls.utils

fun scale(x: Float, minGiven: Float, maxGiven: Float, minTo: Float = 0f, maxTo: Float = 1f): Float {
    return x * ((maxTo - minTo) / (maxGiven - minGiven))
}

@Deprecated("Not used", ReplaceWith("compare in (min + 1)..(max - 1)"))
fun between(compare: Int, min: Int, max: Int): Boolean {
    return compare in (min + 1)..(max - 1)
}

fun between(compare: Float, min: Float, max: Float): Boolean {
    return compare in (min + 1)..(max - 1)
}