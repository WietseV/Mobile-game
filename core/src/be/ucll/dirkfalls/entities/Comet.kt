package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.utils.circle
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2


class Comet(
    startPosition: Vector2,
    private var radius: Float,
    override var velocity: Vector2 = Vector2(0f, -3f)
) : Entity() {
    override var position = startPosition
    override val shape
        get() = Circle(position, radius)

    // Get 8 points of the circle and check if they are contained in the shape
    fun overlaps(entity: Entity) =
        listOf(
            Vector2(shape.x + shape.radius, shape.y),
            Vector2(shape.x + shape.radius / 2, shape.y + shape.radius / 2),
            Vector2(shape.x, shape.y + shape.radius),
            Vector2(shape.x - shape.radius / 2, shape.y + shape.radius / 2),
            Vector2(shape.x - shape.radius, shape.y),
            Vector2(shape.x - shape.radius / 2, shape.y - shape.radius / 2),
            Vector2(shape.x, shape.y - shape.radius),
            Vector2(shape.x + shape.radius / 2, shape.y - shape.radius / 2)
        ).any { entity.shape.contains(it) }

    override fun drawDebug(renderer: ShapeRenderer) = renderer.circle(shape)

    override fun outOfBounds(delta: Float): Boolean =
        (position.x + velocity.x * delta) - radius < 0 ||
                (position.x + velocity.x * delta) + radius > WORLD_WIDTH
}
