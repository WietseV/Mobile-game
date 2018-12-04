package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.screen.DirkScreen
import be.ucll.dirkfalls.screen.HomeScreen
import com.badlogic.gdx.graphics.Color

class UseGyroButton(screen: DirkScreen, color: Color = Color.RED) : Button(screen, color) {
    override fun pressButton(game: DirkFallsGame, targetScreen: DirkScreen?) {
        if (screen is HomeScreen) {
            screen.gameState.useGyro = !screen.gameState.useGyro
        }
    }
}