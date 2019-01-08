package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.screen.DirkScreen
import be.ucll.dirkfalls.screen.GameScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture

class PauseButton(override val screen: GameScreen, color: Color = Color.GREEN) : Button(screen, color) {
    val pauseDrawable = Texture("drawable/pauzeButton.png")
    override fun pressButton(game: Game, targetScreen: DirkScreen?) {
        if (screen.paused) {
            screen.resume()
        } else {
            screen.pause()
        }
    }
}