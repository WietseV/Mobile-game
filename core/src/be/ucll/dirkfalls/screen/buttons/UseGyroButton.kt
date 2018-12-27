package be.ucll.dirkfalls.screen.buttons

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.screen.DirkScreen
import be.ucll.dirkfalls.screen.HomeScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color

class UseGyroButton(screen: DirkScreen, text: String, color: Color = Color.RED) :
        Button(screen, text, color) {
    override fun pressButton(game: DirkFallsGame, targetScreen: DirkScreen?) {
        this.color = startColor
        if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)) {
            if (screen is HomeScreen && color != Color.GRAY) {
                screen.gameState.useGyro = !screen.gameState.useGyro
            }
        }
    }

    override fun touchButton(color: Color) {
        if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)) {
            this.color = color
        }
    }

    override fun touchMovedOff() {
        if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)) {
            this.color = startColor
        }
    }
}