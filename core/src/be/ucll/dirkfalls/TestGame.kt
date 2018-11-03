package be.ucll.dirkfalls

import be.ucll.dirkfalls.screen.GameScreen
import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx

class TestGame : Game() {


    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        setScreen(GameScreen())
    }

}