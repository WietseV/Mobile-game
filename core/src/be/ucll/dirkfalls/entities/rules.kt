package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.GameState
import be.ucll.dirkfalls.utils.vector2.plus
import be.ucll.dirkfalls.utils.vector2.times

typealias Rule = (gameState: GameState, delta: Float) -> Unit

val updatePositionBasedOnVelocity: Rule = { gameState, delta ->
    gameState.entities
        .forEach { it.position += it.velocity * delta }
}

val heroTakesDamageWhenHit: Rule = { gameState, _ ->
    val hero = gameState.hero
    gameState.comets
        .filter { it.overlaps(hero) }
        .forEach {
            gameState.deleteEntity(it)
            hero.hit()
        }
}

val removeCometWhenOutOfBound: Rule = { gameState, _ ->
    gameState.comets
        .filter { it.position.y + 1f < 2f }
        .forEach { gameState.deleteEntity(it) }
}

val heroCannotMoveOutOfBounds: Rule = { gameState, delta ->
    val hero = gameState.hero
    if (hero.outOfBounds(delta)) {
        hero.direction = HeroDirection.STILL
    }
}