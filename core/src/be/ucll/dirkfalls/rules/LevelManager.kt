package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameState

class LevelManager(var gameState: GameState) {

    private val rules = mutableSetOf(
        heroCannotMoveOutOfBounds,
        updatePositionBasedOnVelocity,
        heroTakesDamageWhenHit,
        removeCometWhenOutOfBound,
        createCometSpawner(),
        changeColor,
            newBackgroundAbove1000Points
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