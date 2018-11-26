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

    override var position = startPosition

    override val shape
        get() = Rectangle(position.x, position.y, BOUNDS_RADIUS, BOUNDS_RADIUS)

    override var velocity: Vector2
        get() = when (direction) {
            HeroDirection.LEFT -> Vector2(-MAX_X_SPEED, 0f)
            HeroDirection.RIGHT -> Vector2(MAX_X_SPEED, 0f)
            HeroDirection.STILL -> Vector2.Zero
        }
        set(_) {
            throw NotImplementedError("You should not directly set the velocity of the hero, rather use the direction API")
        }

    var health = 100

    override fun drawDebug(renderer: ShapeRenderer) {
        renderer.rect(shape.x, shape.y, shape.width, shape.height)
    }

    override fun outOfBounds(delta: Float): Boolean =
        (position.x + velocity.x * delta) + BOUNDS_RADIUS > GameConfig.WORLD_WIDTH * 1f
                || (position.x + velocity.x * delta) < 0

    fun hit() {
        if (health != 0) {
            health -= 20
        }
    }
}
