package be.ucll.dirkfalls.entities

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Shape2D
import com.badlogic.gdx.math.Vector2

class Hero(
    startPosition: Vector2 = Vector2.Zero
) : Entity() {

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
            _shape.setPosition(value)
        }

    private var _shape = Rectangle(startPosition.x, startPosition.y, BOUNDS_RADIUS, BOUNDS_RADIUS)
    override var shape: Shape2D
        get() = _shape
        set(value) {
            throw NotImplementedError()
        }

    override fun overlaps(entity: Entity): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override var velocity
        get() = when (direction) {
            HeroDirection.LEFT -> Vector2(-MAX_X_SPEED, 0f)
            HeroDirection.RIGHT -> Vector2(MAX_X_SPEED, 0f)
            HeroDirection.STILL -> Vector2.Zero
        }
        set(value) {
            throw NotImplementedError("You should not directly set the velocity of the hero, rather use the direction API")
        }



    private val alive
        get() = health > 0

    private var health = 100


    override fun init() {
        println("Created hero")
    }

    override fun drawDebug(renderer: ShapeRenderer) = renderer.rect(_shape.x,_shape.y,_shape.width,_shape.height)

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
