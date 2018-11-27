package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.screen.HomeScreen
import be.ucll.dirkfalls.screen.KillScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Screen

class ButtonTouchAdapter(private val screen: Screen): InputAdapter() {
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (screen is HomeScreen) {
            screen.screenPressed(convertScreenXToWorldX(screenX), convertScreenYToWorldY(screenY))
        } else if (screen is KillScreen){
            screen.screenPressed(convertScreenXToWorldX(screenX), convertScreenYToWorldY(screenY))
        }
        return super.touchUp(screenX, screenY, pointer, button)
    }

    private fun convertScreenXToWorldX(x: Int): Float {
        return (x * (GameConfig.WORLD_WIDTH/Gdx.graphics.width))
    }
    private fun convertScreenYToWorldY(y: Int): Float {
        return (y * (GameConfig.WORLD_HEIGHT/Gdx.graphics.height))
    }
}