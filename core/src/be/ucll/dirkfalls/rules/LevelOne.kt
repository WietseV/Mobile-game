package be.ucll.dirkfalls.rules

class LevelOne(rule: Rule = touchScreen) : Level(rule) {

    override var rules = setOf(
            rule,
            heroCannotMoveOutOfBounds,
            updatePositionBasedOnVelocity,
            heroTakesDamageWhenHit,
            scoreWhenCometOutOfBound,
            //fallingLikeSin(),
            createCometSpawner(),
            changeColor
    )

    override val imageBackground = "backgrounds/achtergrondLevel4.jpg"
}