package be.ucll.dirkfalls.rules

import com.badlogic.gdx.graphics.Texture

class LevelOne(rule: Rule = touchScreen) : Level(rule) {

    override var rules = setOf(
            rule,
            heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroTakesDamageWhenHit,
            removeCometWhenOutOfBound,
            createCometSpawner(),
            changeColor
    )

    override val imageBackground = "backgrounds/backgroundLevel1.jpeg"
}