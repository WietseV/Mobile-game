package dirk.ucll.be.dirkfalls.entities

import dirk.ucll.be.dirkfalls.Vector2

class Hero(override var position: Vector2, var size: Vector2, override var velocity: Vector2): Entity {
    var alive: Boolean = false

    override fun init() {
        println("Created hero")
    }

    override fun update(dt: Float) {
        position += velocity * dt
    }

    override fun delete() {
        println("Deleted hero")
    }

    fun hit(){
        alive = false
        //When our hero gets hit,
    }

    fun alive(){
        alive = true
    }
}
