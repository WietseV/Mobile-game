package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.utils.circle
import be.ucll.dirkfalls.utils.vector2.plus
import be.ucll.dirkfalls.utils.vector2.times
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2


class Comet(startPosition: Vector2, override var velocity: Vector2 = Vector2(0f, -3f)) : Entity {
    companion object {
        private const val BOUNDS_RADIUS = 0.1f //world units
    }

    private var _position = startPosition
    override var position: Vector2
        get() = _position
        set(value) {
            _position = value
            bounds.setPosition(value)
        }

    var fallSpeed
        get() = velocity.y
        set(value) {
            velocity.y = value
        }

    var bounds = Circle(position.x, position.y, BOUNDS_RADIUS)

    override fun drawDebug(renderer: ShapeRenderer) = renderer.circle(bounds)

    override fun init() {
        println("Created comet")
    }

    override fun update(dt: Float) {
        position += velocity * dt
    }


    override fun delete() {
        println("Deleted comet")
    }


}