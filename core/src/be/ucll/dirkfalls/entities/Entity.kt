package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.Vector2
import com.badlogic.gdx.graphics.glutils.ShapeRenderer


interface Entity {
    var position: Vector2
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