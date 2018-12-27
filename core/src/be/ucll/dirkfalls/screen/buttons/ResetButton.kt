package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.screen.DirkScreen
import be.ucll.dirkfalls.screen.GameScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.Color

class ResetButton(screen: DirkScreen, color: Color = Color.RED) :
        Button(screen, color) {
    override fun pressButton(game: Game, targetScreen: DirkScreen?) {
        this.color = startColor
        if (screen is GameScreen) {
            screen.resetGame()
        }
    }
}