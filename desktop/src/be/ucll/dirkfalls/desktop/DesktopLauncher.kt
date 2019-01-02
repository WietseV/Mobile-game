package be.ucll.dirkfalls.desktop

import be.ucll.dirkfalls.DirkFallsGame
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

fun main(arg: Array<String>) {
    val config = LwjglApplicationConfiguration().apply {
        width = 480
        height = 800
    }
    val fb = FacebookDesktop()
    LwjglApplication(DirkFallsGame(fb), config)
}
