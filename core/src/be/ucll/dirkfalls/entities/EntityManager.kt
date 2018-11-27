package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.GameState
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

class EntityManager(var gameState: GameState) {

    /**
     * Time since last comet spawned
     */
    private var cometTimer = 0f

    /**
     * List of all rules used.
     * The order is very important here!
     */
    private val rules = mutableListOf(
        heroCannotMoveOutOfBounds,
        updatePositionBasedOnVelocity,
        heroTakesDamageWhenHit,
        removeCometWhenOutOfBound
    )

    fun draw(renderer: ShapeRenderer) {
        gameState.entities.forEach { it.drawDebug(renderer) }
    }

    fun update(delta: Float) {
        rules.forEach { it(gameState, delta) }
        createComet(delta)
    }

    private fun createComet(delta: Float) {
        cometTimer += delta

        if (cometTimer >= GameConfig.COMET_SPAWN_TIME) {
            cometTimer = 0f // reset timer

            val cometRadius = MathUtils.random(0.1f, 0.3f)
            val cometX = MathUtils.random(0f + cometRadius, GameConfig.WORLD_WIDTH - cometRadius)
            val vector2 = Vector2(cometX, GameConfig.WORLD_HEIGHT + cometRadius)
            val comet = Comet(vector2, cometRadius)
            if (checkCometSpawn(comet)) {
                gameState.entities.add(comet)
            }
        }
    }



    private fun checkCometSpawn(comet: Comet) = gameState.comets.none { it.overlaps(comet) }
}