package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameState

abstract class Level(val rule: be.ucll.dirkfalls.rules.Rule) {

    abstract var rules: Set<Rule>

    fun executeRules( gameState: GameState, delta: Float){
        rules.forEach{it(gameState, delta)}
    }
}