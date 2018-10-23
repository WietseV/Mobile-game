package dirk.ucll.be.dirkfalls.entities

import dirk.ucll.be.dirkfalls.Vector2

class Hero(override var position: Vector2, var size: Vector2, override var velocity: Vector2): Entity {
    val alive
        get()= health > 0

    private var health: Int = 100


    override fun init() {
        println("Created hero")
    }

    override fun update(dt: Float) {
        position += velocity * dt
    }

    fun moveRight(dt: Float) {
        velocity =
        position +=
    }

    override fun delete() {
        println("Deleted hero")
    }

    fun hit(){
        health -= 20
    }

    fun respawn(){
        if (!isAlive()){
            health = 100;
        }
    }

    fun isAlive() = alive

    fun getHealth() = health


}
