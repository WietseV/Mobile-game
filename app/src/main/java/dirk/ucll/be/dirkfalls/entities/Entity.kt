package dirk.ucll.be.dirkfalls.entities

import dirk.ucll.be.dirkfalls.Vector2

interface Entity {
    val position: Vector2
    val velocity: Vector2

    /**
     * Happens once: when the object is inited
     */
    fun init()

    /**
     * Update the position, health, ...
     * Get's called a fixed amount of time
     */
    fun update()


    /**
     * Delete the reference
     */
    fun delete()
}