package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.screen.DirkScreen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Rectangle

abstract class Button(val screen: DirkScreen, var color: Color): Rectangle() {
    protected val startColor = color
    abstract fun pressButton(game: DirkFallsGame, targetScreen: DirkScreen? = null)
    fun touchButton(color: Color = Color.ORANGE) {
        this.color = color
    }
    fun touchMovedOff() {
        this.color = startColor
    }
}