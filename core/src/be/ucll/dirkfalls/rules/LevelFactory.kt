package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameState

class LevelFactory(val gameState: GameState) {

    fun createLevel(number: Int) :Level {
        var gyro: Rule
        if (gameState.useGyro) {
            gyro = gyroscope
        } else {
            gyro = touchScreen
        }
        return when (number) {
            //1 -> LevelOne()
            2 -> LevelTwo()
            3 -> LevelThree(gyro)
            /*4 -> LevelFour()
            5 -> LevelFive()
            6 -> LevelSix()
            7 -> LevelSeven()
            8 -> LevelEight()
            9 -> LevelNine()
            10 -> LevelTen()*/
            else -> LevelOne()
        }

    }


}