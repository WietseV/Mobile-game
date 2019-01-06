package be.ucll.dirkfalls.rules

class LevelFour(rule: Rule = touchScreen) : Level(rule) {
    override var rules = setOf(
            rule,
            heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroTakesDamageWhenHit,
            scoreWhenCometOutOfBound,
            createCometsWithVelocity(),
            changeColor
    )

    override val imageBackground = "backgrounds/achtergrondLevelBonus.png"

}