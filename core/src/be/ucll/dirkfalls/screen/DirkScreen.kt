package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.GameConfig.WORLD_HEIGHT
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.GameState
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.math.Vector2

abstract class DirkScreen(val gameState: GameState) : Screen {
    abstract fun touchUp(x: Float, y: Float)
    abstract fun touchDown(x: Float, y: Float)
    abstract fun touchDragged(x: Float, y: Float)
    private var fromWorldToHUDWidth = Gdx.graphics.width / WORLD_WIDTH
    private var fromWorldToHUDHeight = Gdx.graphics.height / WORLD_HEIGHT
    private var fromHUDToWorldWidth = WORLD_WIDTH / Gdx.graphics.width
    private var fromHUDToWorldHeight = WORLD_HEIGHT / Gdx.graphics.height

    @Deprecated("Not used", ReplaceWith("Gdx.graphics.width * percentWidth", "com.badlogic.gdx.Gdx"))
    fun getTextWidthBasedOnScreen(percentWidth: Float) = Gdx.graphics.width * percentWidth

    @Deprecated("Not used", ReplaceWith("Gdx.graphics.height * percentHeight", "com.badlogic.gdx.Gdx"))
    fun getTextHeightBasedOnScreen(percentHeight: Float) = Gdx.graphics.height * percentHeight

    @Deprecated("Not used", ReplaceWith("Vector2(Gdx.graphics.width * percentX - offsetX, Gdx.graphics.height * percentY - offsetY)", "com.badlogic.gdx.math.Vector2", "com.badlogic.gdx.Gdx", "com.badlogic.gdx.Gdx"))
    fun getTextCoordsOnScreen(percentX: Float, percentY: Float, offsetX: Float, offsetY: Float) =
            Vector2(Gdx.graphics.width * percentX - offsetX, Gdx.graphics.height * percentY - offsetY)

    fun getBoxWidthBasedOnScreen(percentWidth: Float) = WORLD_WIDTH * percentWidth

    fun getBoxHeightBasedOnScreen(percentHeight: Float) = WORLD_HEIGHT * percentHeight

    fun getBoxCoordsOnScreen(percentX: Float, percentY: Float, width: Float, height: Float) =
            Vector2(WORLD_WIDTH * percentX - width / 2, WORLD_HEIGHT * percentY - height / 2)

    //om alles mooi om te zetten van renderer naar batch
    @Deprecated("Not used", ReplaceWith("width * fromWorldToHUDWidth"))
    fun fromWorldToHUDWidth(width: Float) = width * fromWorldToHUDWidth

    @Deprecated("Not used", ReplaceWith("height * fromWorldToHUDHeight"))
    fun fromWorldToHUDHeight(height: Float) = height * fromWorldToHUDHeight

    fun fromHUDToWorldWidth(width: Int) = width * fromHUDToWorldWidth

    fun fromHUDToWorldHeight(height: Int) = height * fromHUDToWorldHeight
    // als je wilt dat uw button een mooie vierkant is.
    fun getHeightBasedOnWidth(percentWidth: Float): Float {
        val y = (WORLD_WIDTH * percentWidth) / WORLD_HEIGHT
        return getBoxHeightBasedOnScreen(y)
    }


}