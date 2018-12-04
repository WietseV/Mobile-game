package be.ucll.dirkfalls.rules

class BasicLevelRules() : Level() {
    override var rules = setOf( heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroTakesDamageWhenHit,
            removeCometWhenOutOfBound,
            createCometSpawner())
}