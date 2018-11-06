package be.ucll.dirkfalls.entities

import com.badlogic.gdx.math.Rectangle
import org.w3c.dom.css.Rect

class HealthBar {

    companion object {
        private const val width = 0.1f //world units
    }
    private var _green = Rectangle(0f,0f,0f,width)
    var green: Rectangle?
            get() = _green
            set(value) {
                _green.set(value)
            }
    fun setGreen(x: Float, y: Float, health: Float) {
        green?.set(Rectangle(x, y, health, width))
    }

    private var _red = Rectangle(0f,0f,0f,width)
    var red: Rectangle?
        get() = _red
        set(value) {
            _red.set(value)
        }
    fun setRed(x: Float, y: Float, missing: Float) {
        red?.set(Rectangle(x, y, missing, width))
    }
}