package be.ucll.dirkfalls.entities

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2


interface Entity {
    /**
     * Current position of the entity
     */
    var position: Vector2

    /**
     * Current velocity of the entity
     */
    var velocity: Vector2

    /**
     * Happens once: when the object is inited
     */
    fun init()

    /**
     * Update the position, health, ...
     */
    fun update(dt: Float)


    /**
     * Delete the reference
     */
    fun delete()

    fun drawDebug(renderer: ShapeRenderer)
}