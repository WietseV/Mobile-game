package be.ucll.dirkfalls.rules

class LevelFour(rule: Rule = touchScreen) : Level(rule) {
    override var rules = setOf(
            rule,
            heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroTakesDamageWhenHit,
            removeCometWhenOutOfBound,
            createCometsWithVelocity(),
            changeColor
    )

    override val imageBackground = "backgrounds/level4background.jpg"

}