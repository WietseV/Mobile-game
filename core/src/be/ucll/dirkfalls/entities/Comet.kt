package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.utils.circle
import be.ucll.dirkfalls.utils.vector2.plus
import be.ucll.dirkfalls.utils.vector2.times
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Shape2D
import com.badlogic.gdx.math.Vector2


class Comet(startPosition: Vector2, radius: Float, override var velocity: Vector2 = Vector2(0f, -3f)) : Entity() {

    private var _radius: Float = radius
    var radius: Float
        get() = _radius
        set(value) {
            _radius = value
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

    private var _shape = Circle(position, radius)
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

    override fun outOfBounds(delta: Float): Boolean =
            (position.x + velocity.x * delta) - radius < 0 ||
                    (position.x + velocity.x * delta) + radius > WORLD_WIDTH
}
