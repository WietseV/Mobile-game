package be.ucll.dirkfalls

import be.ucll.dirkfalls.screen.HomeScreen
import be.ucll.dirkfalls.service.FacebookInterface
import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx

class DirkFallsGame(val fb: FacebookInterface) : Game() {
    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        setScreen(HomeScreen(this, GameState()))
    }
}