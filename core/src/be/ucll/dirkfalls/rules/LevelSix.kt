package be.ucll.dirkfalls.rules

class LevelSix(rule: Rule = touchScreen) : Level(rule) {

    override var rules = setOf(
            rule,
            heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroHealsWhenHit,
            heroTakesDamageOverTime(),
            noScoreWhenCometOutOfBound,
            createCometSpawner(),
            changeColor
    )

    override val imageBackground = "backgrounds/achtergrondLevel6.png"
}