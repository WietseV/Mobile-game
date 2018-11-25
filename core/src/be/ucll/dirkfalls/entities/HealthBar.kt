package be.ucll.dirkfalls.entities

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class HealthBar(position: Vector2 = Vector2.Zero) {
    companion object {
        private const val WIDTH = 0.4f //world units
        private const val MAX_HEALTH = 2f
    }

    private var _position = position
    var position: Vector2
        get() = _position
        set(value) {
            _position = value
            green.setPosition(value)
        }

    private var green = Rectangle(position.x, position.y, MAX_HEALTH, WIDTH)
    private var red = Rectangle(position.x, position.y, MAX_HEALTH, WIDTH)

    /**
     * Updates the healthbar with a new health value
     */
    fun update(health: Int) =
        red.set(Rectangle(position.x, position.y, MAX_HEALTH - healthToFloat(health), WIDTH))

    private fun healthToFloat(health: Int) = (MAX_HEALTH / 100) * health

    fun draw(renderer: ShapeRenderer) {
        drawGreen(renderer)
        drawRed(renderer)
    }

    private fun drawGreen(renderer: ShapeRenderer) {
        renderer.setColor(0f, 255f, 0f, 100f)
        renderer.rect(green.x, green.y, green.width, green.height)
    }

    private fun drawRed(renderer: ShapeRenderer) {
        renderer.setColor(255f, 0f, 0f, 100f)
        renderer.rect(red.x, red.y, red.width, red.height)
    }

}