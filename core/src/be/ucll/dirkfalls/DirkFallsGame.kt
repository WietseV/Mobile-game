package be.ucll.dirkfalls

import be.ucll.dirkfalls.screen.HomeScreen
import be.ucll.dirkfalls.utils.AsyncHandler
import be.ucll.dirkfalls.service.HighscoreEntry
import be.ucll.dirkfalls.service.HighscoreService
import be.ucll.dirkfalls.utils.logger
import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx

class DirkFallsGame : Game() {
    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        setScreen(HomeScreen(this, GameState()))
    }
}