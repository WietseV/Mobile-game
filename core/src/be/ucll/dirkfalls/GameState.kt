package be.ucll.dirkfalls

import be.ucll.dirkfalls.entities.Background
import be.ucll.dirkfalls.entities.Comet
import be.ucll.dirkfalls.entities.Entity
import be.ucll.dirkfalls.entities.Hero
import be.ucll.dirkfalls.rules.LevelManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import kotlin.properties.Delegates

class GameState {
    val heroradius = 0.4f
    val hero = Hero(Vector2(GameConfig.WORLD_WIDTH / 2f - heroradius , 1f), heroradius)
    val entities = mutableListOf<Entity>(hero)
    val comets
        get() = entities.filterIsInstance<Comet>()
    var score: Int by Delegates.observable(0) { property, oldValue, newValue ->
        levelManager.nextLevel() // elke keer als score geupdate wordt, meld dit aan de level manager
    }
    var gameOver = false
    val background = Background()
    var imgBackground = Texture("backgrounds/backgroundLevel1.jpeg")
    var pressedPosition: Vector2? = null
    private val levelManager = LevelManager(this)
    var useGyro = false


    fun deleteEntity(entity: Entity) {
        entities.remove(entity)
    }

    fun resetGame() {
        hero.position = Vector2(GameConfig.WORLD_WIDTH / 2f - heroradius, 1f)
        hero.health = 100
        entities.removeAll(comets)
        score = 0
        gameOver = false
        levelManager.resetLevels()
    }

    fun changeBackground(red: Float, green: Float, blue: Float) {
        background.changeColor(red, green, blue)
    }

    fun updateLevels(delta: Float) {
        levelManager.update(delta)
    }

    fun setLevelBackground() {
        imgBackground = levelManager.getBackgroundImg()
    }
}



