package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.utils.circle
import be.ucll.dirkfalls.utils.vector2.plus
import be.ucll.dirkfalls.utils.vector2.times
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2

class Hero(
    startPosition: Vector2 = Vector2.Zero
) : Entity {
    companion object {
        private const val BOUNDS_RADIUS = 0.4f //world units
        private const val MAX_X_SPEED = 5f // world units
    }

    var direction: HeroDirection = HeroDirection.STILL

    private var _position = startPosition
    override var position: Vector2
        get() = _position
        set(value) {
            _position = value
            bounds.setPosition(value)
        }

    override var velocity
        get() = when (direction) {
            HeroDirection.LEFT -> Vector2(-MAX_X_SPEED, 0f)
            HeroDirection.RIGHT -> Vector2(MAX_X_SPEED, 0f)
            HeroDirection.STILL -> Vector2.Zero
        }
        set(value) {
            throw NotImplementedError("You shout not directly set the velocity of the hereo, rather use the direction API")
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
