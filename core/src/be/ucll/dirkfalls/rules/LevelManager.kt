package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameState
import com.badlogic.gdx.graphics.Texture

class LevelManager(var gameState: GameState) {

    private val levelFactory = LevelFactory(gameState)
    private var levelCounter = 1
    private var level: Level = levelFactory.createLevel(levelCounter)


    fun update(delta: Float) {
        level.executeRules(gameState, delta)
    }

    fun nextLevel() {
        if (gameState.score % 25 == 0){
            /*if (levelCounter == 3) {
                resetLevels()
            } else {
                level = levelFactory.createLevel(++levelCounter)
            }*/
            level = levelFactory.createLevel(++levelCounter)

        }
    }

    fun resetLevels() {
        levelCounter = 1
        level = levelFactory.createLevel(levelCounter)
    }

    fun getBackgroundImg():Texture = level.imgBackground

}