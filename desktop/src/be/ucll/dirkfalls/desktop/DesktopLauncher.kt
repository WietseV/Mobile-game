package be.ucll.dirkfalls.desktop

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.GameConfig
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import java.lang.System.setProperty

fun main(arg: Array<String>) {
    setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true")

    val config = LwjglApplicationConfiguration().apply {
        width = GameConfig.WIDTH
        height = GameConfig.HEIGHT
    }
    LwjglApplication(DirkFallsGame(), config)
}
