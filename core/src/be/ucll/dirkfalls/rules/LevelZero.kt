package be.ucll.dirkfalls.rules

import com.badlogic.gdx.graphics.Texture

class LevelZero(rule: Rule = touchScreen) : Level(rule) {
    override var rules = setOf(
            rule,
            updatePositionBasedOnVelocity,
            spawnCometForIntroScreen()

    )
    override val imgBackground = Texture("backgrounds/achtergrond.png")

}