package be.ucll.dirkfalls.rules

class LevelSix(rule: Rule = touchScreen) : Level(rule) {

    override var rules = setOf(
            rule,
            heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroHealsWhenHit,
            heroTakesDamageOverTime(),
            removeCometWhenOutOfBound,
            createCometSpawner(),
            changeColor
    )

    override val imageBackground = "backgrounds/backgroundLevel1.jpeg"
}