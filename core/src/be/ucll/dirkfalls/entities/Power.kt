package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.utils.batchRender
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Shape2D
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

class Power(override var position: Vector2,
            private var radius: Float,
            override var velocity: Vector2 = Vector2(0f, -3f)
) : Entity()  {
    override val shape
            get() = Circle(position, radius)

    override val image = Texture("cometsSprits/superpower.png")
    var color = Vector3(1f, 1f, 1f)

    fun collide(circle: Circle) = shape.overlaps(circle)

    fun collideWithHero(hero: Hero) =
            calculateCollidingCircle().overlaps(hero.calculateCollidingCircle())

    private fun calculateCollidingCircle(): Circle {
        return Circle(shape.x + radius, shape.y + radius, radius)
    }

    override fun drawDebug(renderer: ShapeRenderer) {
        renderer.setColor(color.x, color.y, color.z, 100f)
        renderer.batchRender(shape)
    }

    override fun outOfBounds(delta: Float): Boolean =
            (position.x + velocity.x * delta) - radius < 0 ||
                    (position.x + velocity.x * delta) + radius > GameConfig.WORLD_WIDTH

    override fun size(): Float {
        return 2 * shape.radius
    }

}