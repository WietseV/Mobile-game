package dirk.ucll.be.dirkfalls.entities

import dirk.ucll.be.dirkfalls.Vector2

interface Entity {
    val position: Vector2

    /**
     * Creates a reference on the view
     */
    fun init()

    /**
     * Update the position, health, ...
     * Get's called a fixed amount of time
     */
    fun update()

    /**
     * Draw the entity
     * Can get called 10 times a second, 500 times a second. Should only contain draw logic
     */
    fun draw()

    /**
     * Delete the reference
     */
    fun delete()
}