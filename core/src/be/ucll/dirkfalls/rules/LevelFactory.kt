package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameState

interface LevelFactory {
    val gameState: GameState
    
    fun createLevel(number: Int, level: Level?): Level
}