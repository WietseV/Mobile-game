package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.utils.circle
import be.ucll.dirkfalls.utils.isKeyPressed
import be.ucll.dirkfalls.utils.vector2.plus
import be.ucll.dirkfalls.utils.vector2.times
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2

class Hero(
    startPosition: Vector2 = Vector2(0f, 0f),
    override var velocity: Vector2 = Vector2(2f, 0f)
) : Entity {
    companion object {
        private const val BOUNDS_RADIUS = 0.4f //world units
        private const val MAX_X_SPEED = 0.05f // world units
    }

    private var _position = startPosition
    override var position: Vector2
        get() = _position
        set(value) {
            _position = value
            bounds.setPosition(value)
        }

    var bounds = Circle(position.x, position.y, BOUNDS_RADIUS)

    private val alive
        get() = health > 0

    private var health = 100


    override fun init() {
        println("Created hero")
    }

    override fun update(dt: Float) {
        position += velocity * dt
    }

    /**
     * Om naar links en naar rechts te kunnen bewegen
     */
    fun moveX() {
        position += Vector2(
            when {
                Input.Keys.RIGHT.isKeyPressed() -> MAX_X_SPEED
                Input.Keys.LEFT.isKeyPressed() -> -MAX_X_SPEED
                else -> 0f
            }, 0f
        )
    }

    override fun drawDebug(renderer: ShapeRenderer) = renderer.circle(bounds)

    override fun delete() {
        println("Deleted hero")
    }

    fun hit() {
        health -= 20
    }

    fun respawn() {
        if (!alive) {
            health = 100
        }
    }

}
