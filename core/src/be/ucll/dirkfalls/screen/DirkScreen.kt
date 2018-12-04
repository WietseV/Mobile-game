package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.GameConfig.WORLD_HEIGHT
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.math.Vector2

abstract class DirkScreen: Screen {
    abstract fun touchUp(x: Float, y: Float)
    abstract fun touchDown(x: Float, y: Float)
    abstract fun touchDragged(x: Float, y: Float)
    fun getTextWidthBasedOnScreen(percentWidth: Float) = Gdx.graphics.width*percentWidth

    fun getTextHeightBasedOnScreen(percentHeight: Float) = Gdx.graphics.height*percentHeight

    fun getTextCoordsOnScreen(percentX: Float, percentY: Float, offsetX: Float, offsetY: Float) =
            Vector2(Gdx.graphics.width*percentX-offsetX/2,Gdx.graphics.height*percentY-offsetY/2)

    fun getBoxWidthBasedOnScreen(percentWidth: Float) = WORLD_WIDTH*percentWidth

    fun getBoxHeightBasedOnScreen(percentHeight: Float) = WORLD_HEIGHT*percentHeight

    fun getBoxCoordsOnScreen(percentX: Float, percentY: Float, width: Float, height: Float) =
            Vector2(WORLD_WIDTH*percentX-width/2, WORLD_HEIGHT*percentY-height/2)
}