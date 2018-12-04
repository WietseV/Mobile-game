package be.ucll.dirkfalls.screen

import com.badlogic.gdx.Screen

abstract class DirkScreen: Screen {
    abstract fun touchUp(x: Float, y: Float)
    abstract fun touchDown(x: Float, y: Float)
    abstract fun touchDragged(x: Float, y: Float)
}