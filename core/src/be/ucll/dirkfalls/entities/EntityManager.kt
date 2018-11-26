package be.ucll.dirkfalls.entities

import com.badlogic.gdx.graphics.glutils.ShapeRenderer

/**
 * Handles all the things considering entities
 * Example: Spawning commets when required
 */
interface EntityManager {
    /**
     * The hero entity
     */
    val hero: Hero

    /**
     * List containing all the entities (including the hero)
     */
    val entities: List<Entity>

    /**
     * Draw the entities
     */
    fun draw(renderer: ShapeRenderer)

    /**
     * Update the entities
     */
    fun update(delta: Float)
}

