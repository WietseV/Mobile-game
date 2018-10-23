package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.Vector2


class Comet(override var position: Vector2, override var velocity: Vector2) : Entity {
    override fun init() {
        println("Created comet")
    }

    override fun update(dt: Float) {
        position = velocity * dt
    }

    override fun delete() {
        println("Deleted comet")
    }
}