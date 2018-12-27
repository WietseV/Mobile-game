package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.screen.DirkScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture

class PauseButton(screen: DirkScreen, color: Color = Color.GREEN) :
        Button(screen, color) {
    val pauseDrawable = Texture("drawable/pauzeButton.png")
    override fun pressButton(game: Game, targetScreen: DirkScreen?) {
        screen.pause()
        println("pause")
    }
}