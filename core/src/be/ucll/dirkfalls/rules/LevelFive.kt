package be.ucll.dirkfalls.rules

class LevelFive(rule: Rule = touchScreenInverted) : Level(rule) {

    override var rules = setOf(
            rule,
            heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroTakesDamageWhenHit,
            scoreWhenCometOutOfBound,
            createCometSpawner(),
            changeColor
    )

    override val imageBackground = "backgrounds/achtergrondLevel5.png"
}