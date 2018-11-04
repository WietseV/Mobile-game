package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.utils.vector2.plus
import be.ucll.dirkfalls.utils.vector2.times
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Shape2D
import com.badlogic.gdx.math.Vector2


abstract class Entity {

    abstract var shape: Shape2D
    /**
     * Current position of the entity
     */
    abstract var position: Vector2

    /**
     * Current velocity of the entity
     * This should be how many units the entity moves per second (it get's multiplied with delta time
     * in the update method)
     */
    abstract var velocity: Vector2

    /**
     * Happens once: when the object is inited
     */
    abstract fun init()

    /**
     * Update the position, health, ...
     */
    open fun update(dt: Float) {
        position += velocity * dt
    }

    /**
     * Delete the reference
     */
    abstract fun delete()

    /**
     * Draw debug info
     */
    abstract fun drawDebug(renderer: ShapeRenderer)

    abstract fun overlaps(entity: Entity): Boolean
}