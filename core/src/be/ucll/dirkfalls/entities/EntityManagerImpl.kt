package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.GameConfig
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

class EntityManagerImpl : EntityManager {
    override val hero = Hero(Vector2(GameConfig.WORLD_WIDTH / 2f, 1f))
    override val entities = mutableListOf<Entity>(hero)
    override val comets
        get() = entities.filterIsInstance<Comet>()

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

    override fun draw(renderer: ShapeRenderer) {
        entities.forEach { it.drawDebug(renderer) }
    }

    override fun update(delta: Float) {
        rules.forEach { it(this, delta) }
        createComet(delta)
    }

    override fun deleteEntity(entity: Entity) = entities.remove(entity)

    private fun createComet(delta: Float) {
        cometTimer += delta

        if (cometTimer >= GameConfig.COMET_SPAWN_TIME) {
            cometTimer = 0f // reset timer

            val cometRadius = MathUtils.random(0.1f, 0.3f)
            val cometX = MathUtils.random(0f + cometRadius, GameConfig.WORLD_WIDTH - cometRadius)
            val vector2 = Vector2(cometX, GameConfig.WORLD_HEIGHT + cometRadius)
            val comet = Comet(vector2, cometRadius)
            if (checkCometSpawn(comet)) {
                entities.add(comet)
            }
        }
    }

    private fun checkCometSpawn(comet: Comet): Boolean {
        entities.forEach {
            if (it.overlaps(comet)) {
                return false
            }
        }
        return true
    }
}