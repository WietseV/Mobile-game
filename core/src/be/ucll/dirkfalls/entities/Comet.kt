package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.utils.circle
import be.ucll.dirkfalls.utils.vector2.plus
import be.ucll.dirkfalls.utils.vector2.times
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Shape2D
import com.badlogic.gdx.math.Vector2


class Comet(startPosition: Vector2, override var velocity: Vector2 = Vector2(0f, -3f)) : Entity() {

    companion object {
        private const val BOUNDS_RADIUS = 0.1f //world units
    }

    private var _position = startPosition
    override var position: Vector2
        get() = _position
        set(value) {
            _position = value
            _shape.setPosition(value)
        }

    var fallSpeed
        get() = velocity.y
        set(value) {
            velocity.y = value
        }

    private var _shape = Circle(position, BOUNDS_RADIUS)
    override var shape: Shape2D
        get() = _shape
        set(value) {
            throw NotImplementedError()
        }

    override fun overlaps(entity: Entity) = entity.shape.contains(_shape.x+_shape.radius, _shape.y) ||
            entity.shape.contains(_shape.x+_shape.radius/2, _shape.y+_shape.radius/2) ||
            entity.shape.contains(_shape.x, _shape.y+_shape.radius) ||
            entity.shape.contains(_shape.x-_shape.radius/2, _shape.y+_shape.radius/2) ||
            entity.shape.contains(_shape.x-_shape.radius, _shape.y) ||
            entity.shape.contains(_shape.x-_shape.radius/2, _shape.y-_shape.radius/2) ||
            entity.shape.contains(_shape.x, _shape.y-_shape.radius) ||
            entity.shape.contains(_shape.x+_shape.radius/2, _shape.y-_shape.radius/2)

    override fun drawDebug(renderer: ShapeRenderer) = renderer.circle(_shape)

    override fun init() {
        println("Created comet")
    }

    override fun delete() {
        println("Deleted comet")
    }


}