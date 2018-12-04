package be.ucll.dirkfalls.rules

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
}