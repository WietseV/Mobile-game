package be.ucll.dirkfalls.rules

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

    override val imageBackground = "backgrounds/level3background.jpg"

}
