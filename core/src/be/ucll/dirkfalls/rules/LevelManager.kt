package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameState
import com.badlogic.gdx.graphics.Texture

class LevelManager(var gameState: GameState) {
    private val levelFactory = LevelFactory(gameState)
    private val assetManager = gameState.assetManager
    private var levelCounter = 1
    private var level: Level = loadLevel(levelCounter)

    val backgroundLoaded: Boolean
        get() = assetManager.isLoaded(level.imageBackground)

    val backgroundImg: Texture
        get() = gameState.assetManager.get(level.imageBackground)

    private fun loadLevel(levelCounter: Int): Level {
        val level = levelFactory.createLevel(levelCounter)

        if (!assetManager.isLoaded(level.imageBackground)) {
            assetManager.load(level.imageBackground, Texture::class.java)
        }

        // Preload the next level image
        val nextLevel = levelFactory.createLevel(levelCounter + 1)
        if (level.imageBackground != nextLevel.imageBackground) {
            assetManager.load(nextLevel.imageBackground, Texture::class.java)
        }

        return level
    }

    fun update(delta: Float) {
        level.executeRules(gameState, delta)
    }


    fun nextLevel() {
        if (gameState.score % 25 == 0) {
            val previousLevel = level
            level = loadLevel(++levelCounter)

            val assetManager = gameState.assetManager
            if (assetManager.isLoaded(previousLevel.imageBackground)
                    && previousLevel.imageBackground != level.imageBackground) {
                assetManager.unload(previousLevel.imageBackground)
            }
        }
    }

    fun resetLevels() {
        levelCounter = 1
        level = levelFactory.createLevel(levelCounter)
    }

    fun introLevel() {
        level = levelFactory.createLevel(0)
    }
}