package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.GameConfig
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
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
            shape.setPosition(value)
        }

    override val shape = Rectangle(startPosition.x, startPosition.y, BOUNDS_RADIUS, BOUNDS_RADIUS)

    override var velocity: Vector2
        get() = when (direction) {
            HeroDirection.LEFT -> Vector2(-MAX_X_SPEED, 0f)
            HeroDirection.RIGHT -> Vector2(MAX_X_SPEED, 0f)
            HeroDirection.STILL -> Vector2.Zero
        }
        set(value) {
            throw NotImplementedError("You should not directly set the velocity of the hero, rather use the direction API")
        }

    var health = 100

    override fun drawDebug(renderer: ShapeRenderer) =
        renderer.rect(shape.x, shape.y, shape.width, shape.height)


    fun hit() {
        if (health != 0) {
            health -= 20
        }
    }


    override fun outOfBounds(delta: Float): Boolean =
        (position.x + velocity.x * delta) + BOUNDS_RADIUS > GameConfig.WORLD_WIDTH * 1f
                || (position.x + velocity.x * delta) < 0

}
