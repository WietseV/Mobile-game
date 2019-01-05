package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameState
import com.badlogic.gdx.graphics.Texture

class LevelManager(var gameState: GameState) {
    private val levelFactory: LevelFactory = RandomLevelFactory(gameState)
    private val assetManager = gameState.assetManager
    private var levelCounter = 0
    private var level: Level = loadLevel(levelCounter)
    private lateinit var nextLevel: Level

    val backgroundLoaded: Boolean
        get() = assetManager.isLoaded(level.imageBackground)

    val backgroundImg: Texture
        get() = gameState.assetManager.get(level.imageBackground)

    private fun loadLevel(levelCounter: Int): Level {
        val level = levelFactory.createLevel(levelCounter, null)

        if (!assetManager.isLoaded(level.imageBackground)) {
            assetManager.load(level.imageBackground, Texture::class.java)
        }

        // Preload the next level image
        nextLevel = levelFactory.createLevel(levelCounter + 1, level)
        if (level.imageBackground != nextLevel.imageBackground) {
            assetManager.load(nextLevel.imageBackground, Texture::class.java)
        }

        return level
    }

    private fun loadNextLevel(levelCounter: Int): Level {
        val level = levelFactory.createLevel(levelCounter + 1, level)
        if (!assetManager.isLoaded(level.imageBackground)) {
            assetManager.load(level.imageBackground, Texture::class.java)
        }
        return level
    }

    fun update(delta: Float) {
        level.executeRules(gameState, delta)
    }


    fun nextLevel() {
        if (gameState.score % 25 == 0) {
            val previousLevel = level
            level = nextLevel
            nextLevel = loadNextLevel(++levelCounter)

            val assetManager = gameState.assetManager
            if (assetManager.isLoaded(previousLevel.imageBackground)
                    && previousLevel.imageBackground != level.imageBackground
                    && previousLevel.imageBackground != nextLevel.imageBackground) {
                assetManager.unload(previousLevel.imageBackground)
            }
        }
    }

    fun resetLevels() {
        levelCounter = 1
        level = loadLevel(levelCounter)
    }

    fun introLevel() {
        level = levelFactory.createLevel(0, null)
    }
}