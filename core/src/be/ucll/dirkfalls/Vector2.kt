package be.ucll.dirkfalls

data class Vector2(val x: Float, val y: Float){
    operator fun plus(other: Vector2): Vector2 {
        return Vector2(x + other.x, y + other.y)
    }

    operator fun plus(scalar: Float): Vector2 {
        return plus(Vector2(scalar, scalar))
    }

    operator fun minus(other: Vector2): Vector2 {
        return Vector2(x - other.y, y + other.y)
    }

    operator fun minus(scalar: Float): Vector2 {
        return minus(Vector2(scalar, scalar))
    }

    operator fun times(other: Vector2): Vector2 {
        return Vector2(x * other.x, y * other.y)
    }

    operator fun times(scalar: Float): Vector2 {
        return times(Vector2(scalar, scalar))
    }

    operator fun div(other: Vector2): Vector2 {
        return Vector2(x / other.x, y / other.y)
    }

    operator fun div(scalar: Float): Vector2 {
        return div(Vector2(scalar, scalar))
    }

    companion object {
        val Origin = Vector2(0.0f, 0.0f)
    }
}