package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.screen.DirkScreen
import be.ucll.dirkfalls.screen.GameScreen
import com.badlogic.gdx.graphics.Color

class ResetButton(screen: GameScreen, text: String = "reset", color: Color = Color.RED) : Button(screen, text, color) {
    override fun pressButton(game: DirkFallsGame, targetScreen: DirkScreen?) {
        this.color = startColor
        if (screen is GameScreen) {
            screen.resetGame()
        }
    }
}