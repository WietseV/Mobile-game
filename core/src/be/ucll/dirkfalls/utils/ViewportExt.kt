package be.ucll.dirkfalls.utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport

@JvmOverloads
fun Viewport.drawGrid(renderer: ShapeRenderer, cellSize: Int = 1){
    val oldColor = renderer.color.cpy()
    val doubleWorldWidth = worldWidth * 2
    val doubleWorldHeight = worldHeight * 2

    renderer.use {
        renderer.color = Color.WHITE

        //teken verticale lijnen
        var x =  -doubleWorldWidth
        while (x < doubleWorldWidth){
            renderer.line(x, -doubleWorldHeight, x, doubleWorldHeight)

            x += cellSize
        }

        //teken horizontale lijnen
        var y = -doubleWorldHeight
        while (y < doubleWorldHeight){
            renderer.line(-doubleWorldWidth, y,doubleWorldWidth, y)
            y += cellSize
        }

        //teken de assen
        renderer.color = Color.RED
        renderer.line(0f, -doubleWorldHeight, 0f, doubleWorldHeight)
        renderer.line(-doubleWorldWidth, 0f,doubleWorldWidth, 0f)

        // randen van de wereld
        renderer.color = Color.GREEN
        renderer.line(0f, worldHeight, worldWidth, worldHeight)
        renderer.line(worldWidth, 0f, worldWidth, worldHeight)
    }

    renderer.color = oldColor
}