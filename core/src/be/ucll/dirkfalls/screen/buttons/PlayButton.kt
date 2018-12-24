package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.screen.DirkScreen
import com.badlogic.gdx.graphics.Color

class PlayButton(screen: DirkScreen, text: String , color: Color = Color.RED) : Button(screen, text, color) {
    override fun pressButton(game: DirkFallsGame, targetScreen: DirkScreen?) {
        this.color = startColor
        game.screen = targetScreen!!
    }



}