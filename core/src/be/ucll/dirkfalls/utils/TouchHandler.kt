package be.ucll.dirkfalls.utils

object TouchHandler {
    var isChanged: Boolean = false
    var x: Float = 0f
    var y: Float = 0f

    var touch: FloatArray
        get() {
            isChanged = false
            return floatArrayOf(x, y)
        }
        set(value) {
            x = value.get(0)
            y = value.get(1)
            isChanged = true
        }
}
