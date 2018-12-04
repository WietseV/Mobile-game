package be.ucll.dirkfalls

import be.ucll.dirkfalls.entities.Background
import be.ucll.dirkfalls.entities.Comet
import be.ucll.dirkfalls.entities.Entity
import be.ucll.dirkfalls.entities.Hero
import be.ucll.dirkfalls.rules.LevelManager
import com.badlogic.gdx.math.Vector2
import kotlin.properties.Delegates

class GameState {
    val hero = Hero(Vector2(GameConfig.WORLD_WIDTH / 2f, 1f))
    val entities = mutableListOf<Entity>(hero)
    val comets
        get() = entities.filterIsInstance<Comet>()
    var score: Int by Delegates.observable(0) { property, oldValue, newValue ->
        levelManager.nextLevel() // elke keer als score geupdate wordt, meld dit aan de level manager
    }
    var gameOver = false
    val background = Background()
    var pressedPosition: Vector2? = null
    private val levelManager = LevelManager(this)

    fun deleteEntity(entity: Entity) {
        entities.remove(entity)
    }

    fun resetGame() {
        hero.position = Vector2(GameConfig.WORLD_WIDTH / 2f, 1f)
        hero.health = 100
        entities.removeAll(comets)
        score = 0
        gameOver = false
    }

    fun changeBackground(red: Float, green: Float, blue: Float) {
        background.changeColor(red, green, blue)
    }

    fun updateLevels(delta: Float) {
        levelManager.update(delta)
    }
}



