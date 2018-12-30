package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.utils.batchRender
import be.ucll.dirkfalls.utils.scale
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import kotlin.math.roundToInt

class Hero(
        startPosition: Vector2 = Vector2.Zero,
        var radius: Float = 0.4f
) : Entity() {
    override val image = Texture("cometsSprits/dirk.png")

    companion object {
        private const val MAX_X_SPEED = 5f // world units
        private const val MAX_HEALTH = 100
    }

    var gyro = false

    var direction: HeroDirection = HeroDirection.STILL
    override var position = startPosition

    override val shape
        get() = Circle(position, radius)


    override var velocity: Vector2 = Vector2.Zero
        get() = when {
            gyro -> field
            else -> when (direction) {
                HeroDirection.LEFT -> Vector2(-MAX_X_SPEED, 0f)
                HeroDirection.RIGHT -> Vector2(MAX_X_SPEED, 0f)
                HeroDirection.STILL -> Vector2.Zero
            }
        }
        set(value) {
            if (gyro) {
                field = value
            } else {
                throw NotImplementedError("You should not directly set the velocity of the hero, rather use the direction API")
            }
        }

    var health = MAX_HEALTH

    override fun drawDebug(renderer: ShapeRenderer) {
        renderer.setColor(255f, 255f, 255f, 0f)
        renderer.batchRender(shape)
    }

    override fun outOfBounds(delta: Float): Boolean =
            (position.x + velocity.x * delta) + shape.radius > (GameConfig.WORLD_WIDTH * 1f - radius)
                    || (position.x + velocity.x * delta) < 0

    fun hit(comet: Comet) {
        val damage: Int = (calculateDamage(comet) * 50f).roundToInt()
        takeDamage(damage)
    }

    fun hitHeal(comet: Comet) {
        val heal: Int = (calculateDamage(comet) * 50f).roundToInt()
        heal(heal)
    }

    fun takeDamage(damage: Int) {
        if (health - damage <= 0) {
            health = 0
        } else {
            health -= damage
        }
    }

    fun heal(heal: Int) {
        if (health + heal >= MAX_HEALTH) {
            health = MAX_HEALTH
        } else {
            health += heal
        }
    }

    private fun calculateDamage(comet: Comet): Float = scale(comet.shape.radius, 0f, 0.3f, 0f, 1f)

    override fun size(): Float {
        return 2 * shape.radius
    }

    fun calculateCollidingCircle(): Circle {
        var circle = Circle(shape.x + radius, shape.y + radius, radius)
        return circle
    }
}
