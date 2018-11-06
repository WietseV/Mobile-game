package be.ucll.dirkfalls.entities

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class HealthBar (position: Vector2 = Vector2.Zero) {

    companion object {
        private const val WIDTH = 0.4f //world units
        private const val MAX_HEALTH = 2f
    }

    private var _position = position
    var position: Vector2
        get() = _position
        set(value) {
            _position = value
            _green.setPosition(value)
        }

    private var _green = Rectangle(position.x,position.y, MAX_HEALTH,WIDTH)
    var green: Rectangle?
            get() = _green
            set(value) {
                _green.set(value)
            }

    private var _red = Rectangle(position.x,position.y, MAX_HEALTH,WIDTH)
    var red: Rectangle?
        get() = _red
        set(value) {
            _red.set(value)
        }
    fun setRed(health: Int) = red?.set(Rectangle(position.x, position.y, MAX_HEALTH-healthToFloat(health), WIDTH))

    private fun healthToFloat(health: Int) = (MAX_HEALTH/100)*health

    fun drawDebugGreen(renderer: ShapeRenderer) = renderer.rect(green!!.x,green!!.y,green!!.width,green!!.height)
    fun drawDebugRed(renderer: ShapeRenderer) = renderer.rect(red!!.x,red!!.y,red!!.width,red!!.height)

}