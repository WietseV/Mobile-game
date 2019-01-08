package be.ucll.dirkfalls

import be.ucll.dirkfalls.entities.*
import be.ucll.dirkfalls.rules.Difficulty
import be.ucll.dirkfalls.rules.LevelManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import kotlin.properties.Delegates

class GameState {
    private val heroradius = 0.4f
    val hero = Hero(Vector2(GameConfig.WORLD_WIDTH / 2f - heroradius, 1f), heroradius)
    val entities = mutableListOf<Entity>(hero)
    val comets
        get() = entities.filterIsInstance<Comet>()
    val powers
        get() = entities.filterIsInstance<Power>()
    var score: Int by Delegates.observable(0) { _, _, _ ->
        levelManager.nextLevel() // elke keer als score geupdate wordt, meld dit aan de level manager
    }
    val assetManager = AssetManager()
    var gameOver = false
    val background = Background()
    var pressedPosition: Vector2? = null
    private val levelManager = LevelManager(this)

    var useGyro: Boolean = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)
    var useVibration: Boolean = Gdx.input.isPeripheralAvailable(Input.Peripheral.Vibrator)
    var music: Float = 100f
    var sound: Float = 100f
    var difficulty: Difficulty = Difficulty.EASY

    var  hitSound : Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.mp3"))
    var powerSound = Gdx.audio.newSound(Gdx.files.internal("sounds/power.mp3"))
    var deadSound = Gdx.audio.newSound(Gdx.files.internal("sounds/dead.mp3"))


    fun deleteEntity(entity: Entity) {
        entities.remove(entity)
    }

    fun intro() {
        entities.remove(hero)
        levelManager.introLevel()
    }

    fun resetGame() {
        if (!entities.contains(hero)) entities.add(hero)

        hero.position = Vector2(GameConfig.WORLD_WIDTH / 2f - heroradius, 1f)
        hero.health = 100
        entities.removeAll(comets)
        score = 0
        gameOver = false
        levelManager.resetLevels()

    }

    fun updateLevels(delta: Float) {
        levelManager.update(delta)
    }

    fun properDiffeculty(): Float {
        return when(difficulty){
            Difficulty.EASY -> GameConfig.COMET_SPAWN_TIME_EASY
            Difficulty.MEDIUM -> GameConfig.COMET_SPAWN_TIME_MEDIUM
            Difficulty.HARD -> GameConfig.COMET_SPAWN_TIME_HARD
        }


    }

    fun loadSound(){

    }
    val backgroundLoaded: Boolean
        get() = levelManager.backgroundLoaded
    val levelBackground: Texture
        get() = levelManager.backgroundImg


}



