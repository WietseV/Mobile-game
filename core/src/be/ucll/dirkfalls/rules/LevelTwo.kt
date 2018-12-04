package be.ucll.dirkfalls.rules

class LevelTwo() : Level(){

    override var rules= setOf(
            heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroTakesDamageWhenHit,
            removeCometWhenOutOfBound,
            createCometSpawner(),
            changeColor,
            newBackground(1f, 2f, 88f)
    )
}