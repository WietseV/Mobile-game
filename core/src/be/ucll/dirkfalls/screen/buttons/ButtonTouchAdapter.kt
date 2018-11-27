package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.screen.HomeScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter

class ButtonTouchAdapter(private val screen: HomeScreen): InputAdapter() {
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        screen.screenPressed(convertScreenXToWorldX(screenX), convertScreenYToWorldY(screenY))
        return super.touchUp(screenX, screenY, pointer, button)
    }

    private fun convertScreenXToWorldX(x: Int): Float {
        return (x * (GameConfig.WORLD_WIDTH/Gdx.graphics.width))
    }
    private fun convertScreenYToWorldY(y: Int): Float {
        return (y * (GameConfig.WORLD_HEIGHT/Gdx.graphics.height))
    }
}