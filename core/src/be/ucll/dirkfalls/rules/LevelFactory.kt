package be.ucll.dirkfalls.rules

class LevelFactory {

    fun createLevel(number: Int) :Level {
        when (number) {
            1 -> return LevelOne()
            2 -> return LevelTwo()
            3 -> return LevelThree()
            /*4 -> LevelFour()
            5 -> LevelFive()
            6 -> LevelSix()
            7 -> LevelSeven()
            8 -> LevelEight()
            9 -> LevelNine()
            10 -> LevelTen()*/
            else -> return LevelOne()
        }

    }


}