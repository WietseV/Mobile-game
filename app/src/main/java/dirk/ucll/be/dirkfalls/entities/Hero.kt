package dirk.ucll.be.dirkfalls.entities

import dirk.ucll.be.dirkfalls.Vector2

typealias Dirk = Hero
class Hero(override var position: Vector2, var size: Vector2, var velocity: Vector2, var life: Boolean = false): Entity {
    override fun init() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update() {
        position.x += velocity.x
        position.y += velocity.y
    }

    fun hit(){
        life = false
        //When our hero gets hit,
    }

    fun alive(){
        life = true
    }
}