package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.screen.DirkScreen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture

class PauseButton(screen: DirkScreen, text: String = "pauze", color: Color =Color.GREEN): Button(screen, text, color) {
    val pauseDrawable = Texture("drawable/pauzeButton.png")
    override fun pressButton(game: DirkFallsGame, targetScreen: DirkScreen?) {
        screen.pause()
        println("pause")
    }
}