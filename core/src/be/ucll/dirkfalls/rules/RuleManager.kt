package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameState

class RuleManager(var gameState: GameState) {
    /**
     * List of all rules used.
     * The order is very important here!
     */
    private val rules = mutableListOf(
        heroCannotMoveOutOfBounds,
        updatePositionBasedOnVelocity,
        heroTakesDamageWhenHit,
        removeCometWhenOutOfBound,
        createCometSpawner()
    )

    fun update(delta: Float) {
        rules.forEach { it(gameState, delta) }
    }
}