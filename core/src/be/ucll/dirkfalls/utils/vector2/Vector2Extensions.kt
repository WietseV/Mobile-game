package be.ucll.dirkfalls.utils.vector2

import com.badlogic.gdx.math.Vector2

operator fun Vector2.plus(other: Vector2): Vector2 {
    return Vector2(x + other.x, y + other.y)
}

operator fun Vector2.plus(scalar: Float): Vector2 {
    return plus(Vector2(scalar, scalar))
}

operator fun Vector2.minus(other: Vector2): Vector2 {
    return Vector2(x - other.x, y - other.y)
}

operator fun Vector2.minus(scalar: Float): Vector2 {
    return minus(Vector2(scalar, scalar))
}

operator fun Vector2.times(other: Vector2): Vector2 {
    return Vector2(x * other.x, y * other.y)
}

operator fun Vector2.times(scalar: Float): Vector2 {
    return times(Vector2(scalar, scalar))
}

operator fun Vector2.div(other: Vector2): Vector2 {
    return Vector2(x / other.x, y / other.y)
}

operator fun Vector2.div(scalar: Float): Vector2 {
    return div(Vector2(scalar, scalar))
}