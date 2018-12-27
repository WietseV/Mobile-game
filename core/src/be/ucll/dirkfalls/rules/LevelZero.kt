package be.ucll.dirkfalls.rules


class LevelZero(rule: Rule = touchScreen) : Level(rule) {
    override var rules = setOf(
            rule,
            updatePositionBasedOnVelocity,
            spawnCometForIntroScreen()

    )

    override val imageBackground = "backgrounds/achtergrond.png"
}