package be.ucll.dirkfalls

import be.ucll.dirkfalls.screen.GameScreen
import be.ucll.dirkfalls.screen.HomeScreen
import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx

class DirkFallsGame : Game() {
    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        setScreen(GameScreen())
    }


}