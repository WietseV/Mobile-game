package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.GameState
import be.ucll.dirkfalls.entities.Comet
import be.ucll.dirkfalls.entities.HeroDirection
import be.ucll.dirkfalls.utils.scale
import be.ucll.dirkfalls.utils.vector2.plus
import be.ucll.dirkfalls.utils.vector2.times
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

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
        .filter { it.position.y < 0f }
        .forEach {
            gameState.deleteEntity(it)
            gameState.score++
        }
}

val heroCannotMoveOutOfBounds: Rule = { gameState, delta ->
    val hero = gameState.hero
    if (hero.outOfBounds(delta)) {
        hero.direction = HeroDirection.STILL
    }
}

fun createCometSpawner(): Rule {
    var cometTimer = 0f

    return {gameState, delta ->
        cometTimer += delta

        if (cometTimer >= GameConfig.COMET_SPAWN_TIME) {
            cometTimer = 0f // reset timer

            val cometRadius = MathUtils.random(0.1f, 0.3f)
            val cometX = MathUtils.random(0f + cometRadius, GameConfig.WORLD_WIDTH - cometRadius)
            val vector2 = Vector2(cometX, GameConfig.WORLD_HEIGHT + cometRadius)
            val comet = Comet(vector2, cometRadius)
            if (gameState.comets.none { it.overlaps(comet) }) {
                gameState.entities.add(comet)
            }
        }
    }
}

val changeColor : Rule = {gameState, delta ->
    gameState.comets.forEach {
        it.color = Vector3(scale(it.position.x, 0f, GameConfig.WORLD_WIDTH), scale(it.position.y, 0f, GameConfig.WORLD_HEIGHT), 0.5f)
    }
}