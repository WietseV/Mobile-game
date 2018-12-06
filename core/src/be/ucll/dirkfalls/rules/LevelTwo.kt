package be.ucll.dirkfalls.rules

import com.badlogic.gdx.graphics.Texture

class LevelTwo(rule: Rule = touchScreen) : Level(rule) {

    override var rules= setOf(
            rule,
            heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroTakesDamageWhenHit,
            removeCometWhenOutOfBound,
            createCometSpawner(),
            changeColor,
            newBackground(1f, 2f, 88f)
    )

    override val imgBackground = Texture("../android/assets/backgrounds/level2background.jpg")

}