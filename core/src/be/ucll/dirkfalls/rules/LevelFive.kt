package be.ucll.dirkfalls.rules

class LevelFive(rule: Rule = touchScreenInverted) : Level(rule) {

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