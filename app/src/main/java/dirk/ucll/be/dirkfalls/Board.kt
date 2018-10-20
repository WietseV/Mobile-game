package dirk.ucll.be.dirkfalls

import dirk.ucll.be.dirkfalls.entities.Entity
import dirk.ucll.be.dirkfalls.entities.Hero
import dirk.ucll.be.dirkfalls.renderer.BoardRenderer

class Board(val width: Float, val height: Float, private val renderer: BoardRenderer) {
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
        renderer.render(this)
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