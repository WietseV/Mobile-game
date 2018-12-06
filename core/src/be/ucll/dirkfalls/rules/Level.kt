package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameState
import com.badlogic.gdx.graphics.Texture

abstract class Level(val rule: be.ucll.dirkfalls.rules.Rule) {

    abstract var rules: Set<Rule>

    fun executeRules( gameState: GameState, delta: Float){
        rules.forEach{it(gameState, delta)}
    }

    abstract val imgBackground: Texture
}