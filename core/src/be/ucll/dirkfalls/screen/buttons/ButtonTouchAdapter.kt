package be.ucll.dirkfalls.screen.buttons

import com.badlogic.gdx.InputAdapter

class ButtonTouchAdapter: InputAdapter() {
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {

        return super.touchUp(screenX, screenY, pointer, button)
    }
}