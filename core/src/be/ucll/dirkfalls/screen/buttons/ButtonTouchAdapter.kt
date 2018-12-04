package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.GameConfig.WORLD_HEIGHT
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.screen.DirkScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import be.ucll.dirkfalls.utils.scale

class ButtonTouchAdapter(private val screen: DirkScreen): InputAdapter() {
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        screen.touchUp(convertScreenXToWorldX(screenX), convertScreenYToWorldY(screenY))
        return super.touchUp(screenX, screenY, pointer, button)
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        screen.touchDown(convertScreenXToWorldX(screenX), convertScreenYToWorldY(screenY))
        return super.touchDown(screenX, screenY, pointer, button)
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        screen.touchDragged(convertScreenXToWorldX(screenX), convertScreenYToWorldY(screenY))
        return super.touchDragged(screenX, screenY, pointer)
    }

    private fun convertScreenXToWorldX(x: Int): Float {
        return scale(x.toFloat(), 0f, Gdx.graphics.width.toFloat(), 0f, WORLD_WIDTH)
    }
    private fun convertScreenYToWorldY(y: Int): Float {
        return WORLD_HEIGHT-scale(y.toFloat(), 0f, Gdx.graphics.height.toFloat(), 0f, WORLD_HEIGHT)
    }
}