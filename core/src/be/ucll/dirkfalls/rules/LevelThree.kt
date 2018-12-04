package be.ucll.dirkfalls.rules

class LevelThree() : Level(){

    override var rules = setOf(
            heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroTakesDamageWhenHit,
            removeCometWhenOutOfBound,
            createCometSpawner(),
            changeColor,
            newBackground(200f, 27f, 9f)
    )

}
