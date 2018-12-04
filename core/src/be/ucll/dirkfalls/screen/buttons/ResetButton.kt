package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.screen.DirkScreen
import be.ucll.dirkfalls.screen.GameScreen
import com.badlogic.gdx.graphics.Color

class ResetButton(screen: GameScreen, color: Color = Color.RED) : Button(screen, color) {
    override fun pressButton(game: DirkFallsGame, targetScreen: DirkScreen?) {
        if (screen is GameScreen) {
            screen.resetGame()
        }
    }
}