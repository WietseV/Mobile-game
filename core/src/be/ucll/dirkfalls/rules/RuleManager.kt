package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameState

class RuleManager(var gameState: GameState) {
    /**
     * List of all rules used.
     * The order is very important here!
     */
    private val rules = mutableSetOf(
        heroCannotMoveOutOfBounds,
        updatePositionBasedOnVelocity,
        heroTakesDamageWhenHit,
        removeCometWhenOutOfBound,
        createCometSpawner(),
        changeColor
    )

    fun update(delta: Float) {
        rules.forEach { it(gameState, delta) }
    }

    fun activateRule(rule: Rule) {
        rules.add(rule)
    }

    fun deactivate(rule: Rule) {
        rules.remove(rule)
    }

}