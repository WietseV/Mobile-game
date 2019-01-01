package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameState

class RandomLevelFactory(override val gameState: GameState) : LevelFactory {
    override fun createLevel(number: Int, level: Level?): Level {
        if (number == 0) {
            return LevelZero()
        } else if (number == 1) {
            return LevelOne()
        }
        val gyro: Rule = if (gameState.useGyro) {
            gyroscope
        } else {
            touchScreen
        }
        var levels = listOf(LevelTwo(), LevelThree(gyro), LevelFour(), LevelFive(), LevelSix())
        if (level != null) {
            levels = levels.filter { it.javaClass.name != level.javaClass.name }
        }
        return levels.random()
    }

}