package be.ucll.dirkfalls.screen

import com.badlogic.gdx.Screen

abstract class DirkScreen: Screen {
    abstract fun screenPressed(x: Float, y: Float)
}