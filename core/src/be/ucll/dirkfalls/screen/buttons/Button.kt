package be.ucll.dirkfalls.screen.Buttons

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle

abstract class Button: Rectangle() {
    init {
        Gdx.input.inputProcessor = ButtonTouchAdapter(this)
    }

    abstract fun pressButton()
}