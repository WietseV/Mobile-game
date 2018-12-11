package be.ucll.dirkfalls.rules

import com.badlogic.gdx.graphics.Texture

class LevelFour(rule: Rule = touchScreen) : Level(rule) {
    override var rules = setOf(
            rule,
            heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroTakesDamageWhenHit,
            removeCometWhenOutOfBound,
            createCometsWithVelocity(),
            changeColor,
            newBackground(200f, 27f, 9f)
    )

    override val imgBackground = Texture("backgrounds/level3Background.jpg")

}