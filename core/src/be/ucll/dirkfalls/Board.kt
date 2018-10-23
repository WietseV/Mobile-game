package be.ucll.dirkfalls

import be.ucll.dirkfalls.entities.Entity
import be.ucll.dirkfalls.entities.Hero

class Board(val width: Float, val height: Float) {
    private val _entities = mutableListOf<Entity>()

    val entities: List<Entity>
        get() = _entities

    init {
        addEntity(
                Hero(Vector2.Origin, Vector2(5f, 5f), Vector2(10f, 10f))
        )

    }

    fun updateEntities(dt: Float) {
        _entities.forEach { it.update(dt) }
    }


    private fun addEntity(ent: Entity) {
        _entities.add(ent)
    }

    private fun addEntities(vararg ent: Entity) {
        _entities.addAll(ent)

    }

    private fun removeEntity(ent: Entity) {
        _entities.remove(ent)
    }

    private fun removeEntities(vararg ent: Entity) {
        _entities.removeAll(ent)
    }
}
