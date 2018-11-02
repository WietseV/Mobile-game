package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.Vector2
import be.ucll.dirkfalls.utils.circle
import be.ucll.dirkfalls.utils.isKeyPressed
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle


class Comet(override var position: Vector2, override var velocity: Vector2 = Vector2(2f, 0f)) : Entity {

    companion object {
        // constanten
        private const val BOUNDS_RADIUS = 0.1f //world units
        private const val SIZE = BOUNDS_RADIUS * 2f

        private const val MAX_Y_SPEED = 0.05f // world units


    }

    var bounds: Circle
    var fallSpeed: Float

    init {
        bounds = Circle(position.x, position.y, BOUNDS_RADIUS)
        fallSpeed = MAX_Y_SPEED
    }

    fun fallDown(){
        var updateVector2: Vector2 = Vector2(0f, fallSpeed)


        position -= updateVector2
        updateBounds()
    }
    private fun updateBounds() = bounds.setPosition(position.x, position.y)


    override fun drawDebug(renderer: ShapeRenderer) = renderer.circle(bounds)
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