package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.utils.scale
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle

class Background {
    val background = Rectangle(0f, 1f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT)
    var red = scale(65f, 0f, 255f)
    var green= scale (186f, 0f, 255f)
    var blue = scale (199f, 0f, 255f)
    var color = Color(red, green, blue, 1f)

    fun changeColor(red: Float, green: Float, blue:Float){
        var redP = scale(red, 0f, 255f)
        var greenP = scale(green, 0f, 255f)
        var blueP = scale(blue, 0f, 255f)

        color = Color(redP, greenP, blueP, 1f)
    }

}
