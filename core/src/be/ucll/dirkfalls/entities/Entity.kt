package be.ucll.dirkfalls.entities

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Shape2D
import com.badlogic.gdx.math.Vector2

/**
 * This should be stupid classe: contain no game logic
 * Logic needs to be implented in rules
 */
abstract class Entity {
    /**
     * Shape of the object
     */
    abstract val shape: Shape2D

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
     * Sprits of the enitity
     */

    abstract val image: Texture
    /**
     * Draw debug info
     */
    abstract fun drawDebug(renderer: ShapeRenderer)

    /**
     * Checks if an object is out of bounds
     */
    abstract fun outOfBounds(delta: Float): Boolean

    abstract fun size(): Float
}