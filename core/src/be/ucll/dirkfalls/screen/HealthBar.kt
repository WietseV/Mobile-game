package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.utils.scale
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class HealthBar(private var position: Vector2 = Vector2.Zero) {
    companion object {
        private const val WIDTH = 0.4f //world units
        private const val MAX_HEALTH = 2f
    }

    //health colour
    var redCH = scale(12f, 0f, 255f)
    var greenCH = scale (93f, 0f, 255f)
    var blueCH = scale (23f, 0f, 255f)
    //damage colour
    var redCD = scale(198f, 0f, 255f)
    var greenCD = scale (25f, 0f, 255f)
    var blueCD = scale (25f, 0f, 255f)
    private var green = Rectangle(position.x, position.y, MAX_HEALTH, WIDTH)
    private var red = Rectangle(position.x, position.y, MAX_HEALTH, WIDTH)

    /**
     * Updates the healthbar with a new health value
     */
    fun update(health: Int) {
        val healthInWorldUnits = (MAX_HEALTH / 100) * health
        red.set(Rectangle(position.x, position.y, MAX_HEALTH - healthInWorldUnits, WIDTH))
    }

    fun draw(renderer: ShapeRenderer) {
        drawGreen(renderer)
        drawRed(renderer)
    }

    private fun drawGreen(renderer: ShapeRenderer) {

        renderer.setColor(redCH, greenCH, blueCH, 100f)
        renderer.rect(green.x, green.y, green.width, green.height)
    }

    private fun drawRed(renderer: ShapeRenderer) {
        renderer.setColor(redCD, greenCD, blueCD, 100f)
        renderer.rect(red.x, red.y, red.width, red.height)
    }

}