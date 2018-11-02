package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.Vector2
import be.ucll.dirkfalls.utils.circle
import be.ucll.dirkfalls.utils.isKeyPressed
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle

class Hero(override var position: Vector2 = Vector2(0f,0f), override var velocity: Vector2 = Vector2(2f, 0f)): Entity {
    //vorstellen met een circel, daarna spirit

    companion object {
        // constanten
        private const val BOUNDS_RADIUS = 0.4f //world units
        private const val SIZE = BOUNDS_RADIUS * 2f

        private const val MAX_X_SPEED = 0.05f // world units


    }

    //properties
    var bounds: Circle
    var xSpeed = 0f
    //init

    init {
        bounds = Circle(position.x,position.y, BOUNDS_RADIUS)

    }
    private val alive
        get()= health > 0

    private var health = 100


    override fun init() {
        println("Created hero")
    }

    override fun update(dt: Float) {
        position += velocity * dt
        updateBounds()
    }

    //Om naar links en naar rechts te kunnen bewegen
    fun moveX(){
        xSpeed = 0f

        when{
            Input.Keys.RIGHT.isKeyPressed() -> xSpeed = MAX_X_SPEED
            Input.Keys.LEFT.isKeyPressed() -> xSpeed = - MAX_X_SPEED
        }

        //aangezin roobin wilt dat we met vectors werken, moet ik speed in een vector steken voordat het macheert

        var updateVector2: Vector2 = Vector2(xSpeed, 0f)

        position += updateVector2
        updateBounds()
    }
    private fun updateBounds() = bounds.setPosition(position.x, position.y)


    override fun drawDebug(renderer: ShapeRenderer) = renderer.circle(bounds)
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
