package dirk.ucll.be.dirkfalls.entities

import dirk.ucll.be.dirkfalls.Vector2

class Comet(override var position: Vector2, override var velocity: Vector2) : Entity {
    override fun init() {
        println("Created comet")
    }

    override fun update(dt: Float) {
        fall(dt)
    }

    private fun fall(dt: Float) {
        position = velocity * dt
    }

    override fun delete() {
        println("Deleted comet")
    }
}