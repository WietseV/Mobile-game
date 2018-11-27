package be.ucll.dirkfalls

import be.ucll.dirkfalls.entities.Comet
import be.ucll.dirkfalls.entities.Entity
import be.ucll.dirkfalls.entities.Hero
import com.badlogic.gdx.math.Vector2

class GameState {
    val hero = Hero(Vector2(GameConfig.WORLD_WIDTH / 2f, 1f))
    val entities = mutableListOf<Entity>(hero)
    val comets
        get() = entities.filterIsInstance<Comet>()
    var score = 0
    var gameOver = false

    fun deleteEntity(entity: Entity){
        entities.remove(entity)
    }

    fun resetGame() {
        hero.position = Vector2(GameConfig.WORLD_WIDTH / 2f, 1f)
        hero.health = 100
        entities.removeAll(comets)
        score = 0
        gameOver = false
    }
}