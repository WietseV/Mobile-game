package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.screen.DirkScreen
import com.badlogic.gdx.graphics.Color

class HomeButton(screen: DirkScreen, color: Color = Color.RED) : Button(screen, color) {
    override fun pressButton(game: DirkFallsGame, targetScreen: DirkScreen?) {
        game.screen = targetScreen!!
    }
}
