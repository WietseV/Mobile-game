package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.screen.DirkScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture

open class OptionsButton(screen: DirkScreen, color: Color = Color.RED) : Button(screen, color) {
    val optionsDrawable = Texture("drawable/global-settings.png")
    override fun pressButton(game: Game, targetScreen: DirkScreen?) {
        this.color = startColor
        game.screen = targetScreen
    }
}