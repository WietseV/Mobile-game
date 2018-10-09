package dirk.ucll.be.dirkfalls

import dirk.ucll.be.dirkfalls.entities.Entity

class Board(val width: Float, val height: Float) {
    private val entities = mutableListOf<Entity>()

    fun updateEntities() {
        entities.forEach { it.update() }
        entities.forEach { it.draw() }
    }
}