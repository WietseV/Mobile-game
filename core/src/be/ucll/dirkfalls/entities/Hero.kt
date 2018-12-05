package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.utils.scale
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import kotlin.math.roundToInt

class Hero(
    startPosition: Vector2 = Vector2.Zero
) : Entity() {

    override val image = Texture("../android/assets/cometsSprits/dirk.png")
    companion object {
        private const val BOUNDS_RADIUS = 0.8f //world units
        private const val MAX_X_SPEED = 5f // world units
    }
    var gyro = false

    var direction: HeroDirection = HeroDirection.STILL

    override var position = startPosition

    override val shape
        get() = Rectangle(position.x, position.y, BOUNDS_RADIUS, BOUNDS_RADIUS)

    override var velocity: Vector2 = Vector2.Zero
        get() = when{
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

    var health = 100

    override fun drawDebug(renderer: ShapeRenderer) {
        renderer.setColor(255f, 255f, 255f, 0f)
        //renderer.rect(shape.x, shape.y, shape.width, shape.height)
    }

    override fun outOfBounds(delta: Float): Boolean =
        (position.x + velocity.x * delta) + BOUNDS_RADIUS > GameConfig.WORLD_WIDTH * 1f
                || (position.x + velocity.x * delta) < 0

    fun hit(comet: Comet) {
        val damage: Int = (calculateDamage(comet)*50f).roundToInt()
        if (health - damage <= 0) {
            health = 0
        } else {
            health -= damage
        }
    }

    private fun calculateDamage(comet: Comet): Float = scale(comet.shape.radius, 0f, 0.3f, 0f, 1f)

    override fun size(): Float {
        return BOUNDS_RADIUS
    }
}
