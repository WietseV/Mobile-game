package be.ucll.dirkfalls.rules

import com.badlogic.gdx.graphics.Texture

class LevelTwo(rule: Rule = touchScreen) : Level(rule) {

    override var rules = setOf(
            rule,
            heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroTakesDamageWhenHit,
            removeCometWhenOutOfBound,
            createCometSpawnerAndSize(),
            changeColor
    )

    override val imageBackground = "backgrounds/level2background.jpg"

}