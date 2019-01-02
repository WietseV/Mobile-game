package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.GameConfig.WORLD_HEIGHT
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.GameState
import be.ucll.dirkfalls.screen.buttons.PauseButton
import be.ucll.dirkfalls.utils.scale
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2

internal class GameTouchAdapter(private val gameState: GameState, private val pause: PauseButton) : InputAdapter() {

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (!checkPauseButton(screenX, screenY)) {
            updatePosition(screenX, screenY)
        }
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        if (!checkPauseButton(screenX, screenY)) {
            updatePosition(screenX, screenY)
        }
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (checkPauseButton(screenX, screenY)) {
            pause.pressButton((pause.screen as GameScreen).game, null)
        }
        gameState.pressedPosition = null
        return true
    }

    private fun updatePosition(x: Int, y: Int) {
        gameState.pressedPosition = Vector2(
                scale(x.toFloat(), 0f, Gdx.graphics.width.toFloat(), 0f, WORLD_WIDTH),
                scale(y.toFloat(), 0f, Gdx.graphics.height.toFloat(), 0f, WORLD_HEIGHT)
        )
    }

    private fun checkPauseButton(x: Int, y: Int) = pause.contains(pause.screen.fromHUDToWorldWidth(x), WORLD_HEIGHT-pause.screen.fromHUDToWorldHeight(y))
}