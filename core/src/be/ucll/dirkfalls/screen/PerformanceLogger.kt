package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.utils.logger

class PerformanceLogger {
    // Used for performance calculations
    private val log = logger<PerformanceLogger>()
    private var fps = 0
    private var time = 0f
    private var longestDelta = 0f

    fun update(delta: Float) {
        fps += 1
        time += delta

        if (delta > longestDelta) {
            longestDelta = delta
        }

        if (time >= 1) {
            log()
            reset()
        }
    }

    private fun log() {
        val longestDeltaMs = longestDelta * 1000

        if (longestDeltaMs > 50) {
            log.info("LAGGING!!!")
        }

        log.info("Frames per second: $fps - Longest time between 2 frames: $longestDeltaMs ms (+${longestDeltaMs - 1000f / 60f})")

    }

    private fun reset() {
        fps = 0
        time = 0f
        longestDelta = 0f
    }
}
