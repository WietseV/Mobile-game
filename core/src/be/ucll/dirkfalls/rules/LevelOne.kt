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
            changeColor,
            newBackground(233f, 27f, 213f),
            updateImgBackground
    )

    override val imgBackground = Texture("backgrounds/backgroundLevel1.jpeg ")
}