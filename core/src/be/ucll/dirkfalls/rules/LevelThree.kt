package be.ucll.dirkfalls.rules

import com.badlogic.gdx.graphics.Texture

class LevelThree(rule: Rule = touchScreen) : Level(rule) {

    override var rules = setOf(
            rule,
            heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroTakesDamageWhenHit,
            removeCometWhenOutOfBound,
            createCometsWithVelocity(),
            changeColor
    )

    override val imgBackground = Texture("backgrounds/level3background.jpg")

}
