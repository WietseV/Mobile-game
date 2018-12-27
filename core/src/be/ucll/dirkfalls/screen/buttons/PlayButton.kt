package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.screen.DirkScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.Color

class PlayButton(screen: DirkScreen, color: Color = Color.RED) :
        Button(screen, color) {
    override fun pressButton(game: Game, targetScreen: DirkScreen?) {
        screen.gameState.resetGame()
        this.color = startColor
        game.screen = targetScreen!!
    }


}