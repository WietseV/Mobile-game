package be.ucll.dirkfalls.screen.buttons

import com.badlogic.gdx.InputAdapter

class ButtonTouchAdapter(val b: Button): InputAdapter() {
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        b.pressButton()
        return super.touchUp(screenX, screenY, pointer, button)
    }
}