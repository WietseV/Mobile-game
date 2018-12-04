package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameState

abstract class Level {

    abstract var rules: Set<Rule>

    fun executeRules( gameState: GameState, delta: Float){
        rules.forEach{it(gameState, delta)}
    }
}