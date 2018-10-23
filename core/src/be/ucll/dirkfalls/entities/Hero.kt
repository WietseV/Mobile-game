package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.Vector2

class Hero(override var position: Vector2, var size: Vector2, override var velocity: Vector2): Entity {
    private val alive
        get()= health > 0

    private var health = 100


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
        health -= 20
    }

    fun respawn(){
        if (!alive){
            health = 100
        }
    }

}
