package be.ucll.dirkfalls.rules

class BasicLevelRules(rule: Rule) : Level(rule) {
    override var rules = setOf( heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroTakesDamageWhenHit,
            removeCometWhenOutOfBound,
            createCometSpawner())
}