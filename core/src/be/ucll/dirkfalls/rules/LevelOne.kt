package be.ucll.dirkfalls.rules

class LevelOne() : Level() {

    override var rules = setOf(
            heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroTakesDamageWhenHit,
            removeCometWhenOutOfBound,
            createCometSpawner(),
            changeColor,
            newBackground(233f, 27f, 213f)
    )
}