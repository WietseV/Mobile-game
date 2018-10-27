package be.ucll.dirkfalls.desktop

import be.ucll.dirkfalls.DirkFallsGame
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

fun main(args: Array<String>) {
    LwjglApplication(DirkFallsGame(), LwjglApplicationConfiguration())

}