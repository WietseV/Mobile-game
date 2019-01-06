package be.ucll.dirkfalls.rules


class LevelZero(rule: Rule = touchScreen) : Level(rule) {
    override var rules = setOf(
            rule,
            updatePositionBasedOnVelocity,
            spawnCometForIntroScreen(),
            deleteComentsOutOfBounds

    )

    override val imageBackground = "backgrounds/achtergrond.png"
}