package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameState

class LevelManager(var gameState: GameState) {


    private var level: Level = LevelOne()
    private var levelCounter = 1
    private val LevelFactory = LevelFactory()

    fun update(delta: Float) {
        level.executeRules(gameState, delta)
    }

    fun nextLevel() {
        if (gameState.score % 10 == 0){
            level = LevelFactory.createLevel(++levelCounter)
        }
    }


}