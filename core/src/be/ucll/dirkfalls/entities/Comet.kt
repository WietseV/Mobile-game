package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.utils.circle
import be.ucll.dirkfalls.utils.vector2.minus
import be.ucll.dirkfalls.utils.vector2.times
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2


class Comet(startPosition: Vector2, override var velocity: Vector2 = Vector2(2f, 0f)) : Entity {
    companion object {
        private const val BOUNDS_RADIUS = 0.1f //world units
        private const val SIZE = BOUNDS_RADIUS * 2f
        private const val MAX_Y_SPEED = 0.05f // world units
    }

    private var fallSpeed = MAX_Y_SPEED


    private var _position = startPosition
    override var position: Vector2
        get() = _position
        set(value) {
            _position = value
            bounds.setPosition(value)
        }

    var bounds = Circle(position.x, position.y, BOUNDS_RADIUS)


    fun fallDown() {
        position -= Vector2(0f, fallSpeed)
    }

    override fun drawDebug(renderer: ShapeRenderer) = renderer.circle(bounds)

    override fun init() {
        println("Created comet")
    }

    override fun update(dt: Float) {
        position = velocity * dt
    }

    override fun delete() {
        println("Deleted comet")
    }


}