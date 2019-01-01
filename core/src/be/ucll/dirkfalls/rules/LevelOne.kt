package be.ucll.dirkfalls.rules

class LevelOne(rule: Rule = touchScreen) : Level(rule) {

    override var rules = setOf(
            rule,
            heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroTakesDamageWhenHit,
            scoreWhenCometOutOfBound,
            createCometSpawner(),
            changeColor
    )

    override val imageBackground = "backgrounds/backgroundLevel1.jpeg"
}