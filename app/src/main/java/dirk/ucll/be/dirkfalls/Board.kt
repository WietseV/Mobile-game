package dirk.ucll.be.dirkfalls

import dirk.ucll.be.dirkfalls.entities.Entity
import dirk.ucll.be.dirkfalls.entities.Hero

class Board(val width: Float, val height: Float) {
    private val entities = mutableListOf<Entity>()


    fun updateEntities() {
        entities.forEach { it.update() }
    }

    fun addEntity(ent: Entity) {
        entities.add(ent)
    }

    fun addEntities(vararg ent: Entity) {
        entities.addAll(ent)
    }

    fun removeEntity(ent: Entity) {
        entities.remove(ent)
    }

    fun removeEntities(vararg ent: Entity) {
        entities.removeAll(ent)
    }
}