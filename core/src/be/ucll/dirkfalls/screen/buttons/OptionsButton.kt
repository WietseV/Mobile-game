package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.screen.DirkScreen
import be.ucll.dirkfalls.screen.HomeScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color

class OptionsButton(screen: DirkScreen, color: Color = Color.RED) : Button(screen, color) {
    override fun pressButton(game: Game, targetScreen: DirkScreen?) {
        this.color = startColor
        game.screen = targetScreen
    }
}