package be.ucll.dirkfalls.utils

import be.ucll.dirkfalls.GameConfig.WORLD_HEIGHT
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH

object TouchHandler { //Static class
    var isChanged: Boolean = false //Als dit op true staat is er een touch geweest, als dit op false staat is deze touch uitgelezen
    var x: Float = 0f
    var y: Float = 0f

    var touch: FloatArray?
        get() {
            if (isChanged) {
                isChanged = false
                return floatArrayOf(x, y)
            }
            return null
        }
        set(value) {
            x = value!![0]*WORLD_WIDTH //Werkelijk punt op scherm
            y = value[1]*WORLD_HEIGHT
            isChanged = true
        }
}
