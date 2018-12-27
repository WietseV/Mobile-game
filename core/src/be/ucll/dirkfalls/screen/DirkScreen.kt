package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.GameConfig.WORLD_HEIGHT
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.GameState
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.math.Vector2

abstract class DirkScreen(private val dirkFallsGame: DirkFallsGame, val gameState: GameState) :
        Screen {
    abstract fun touchUp(x: Float, y: Float)
    abstract fun touchDown(x: Float, y: Float)
    abstract fun touchDragged(x: Float, y: Float)
    var fromWorldToHUDWidth = Gdx.graphics.width / WORLD_WIDTH
    var fromWorldToHUDHeight = Gdx.graphics.height / WORLD_HEIGHT
    fun getTextWidthBasedOnScreen(percentWidth: Float) = Gdx.graphics.width * percentWidth

    fun getTextHeightBasedOnScreen(percentHeight: Float) = Gdx.graphics.height * percentHeight

    fun getTextCoordsOnScreen(percentX: Float, percentY: Float, offsetX: Float, offsetY: Float) =
            Vector2(Gdx.graphics.width * percentX - offsetX, Gdx.graphics.height * percentY - offsetY)

    fun getBoxWidthBasedOnScreen(percentWidth: Float) = WORLD_WIDTH * percentWidth

    fun getBoxHeightBasedOnScreen(percentHeight: Float) = WORLD_HEIGHT * percentHeight

    fun getBoxCoordsOnScreen(percentX: Float, percentY: Float, width: Float, height: Float) =
            Vector2(WORLD_WIDTH * percentX - width / 2, WORLD_HEIGHT * percentY - height / 2)

    //om alles mooi om te zetten van renderer naar batch
    fun fromWorldToHUDWidth(width: Float) = width * fromWorldToHUDWidth

    fun fromWorldToHUDHeight(height: Float) = height * fromWorldToHUDHeight
    // als je wilt dat uw button een mooie vierkant is.
    fun getHeightBasedOnWidth(percentWidth: Float): Float {
        var y = (WORLD_WIDTH * percentWidth) / WORLD_HEIGHT
        return getBoxHeightBasedOnScreen(y)
    }


}