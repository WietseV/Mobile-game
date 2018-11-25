package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.entities.Hero
import be.ucll.dirkfalls.entities.HeroDirection.*
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import kotlin.math.roundToInt

internal class GameTouchAdapter(private val hero: Hero) : InputAdapter() {
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        switchHeroDirection(screenX)
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        switchHeroDirection(screenX)
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        hero.direction = STILL
        return true
    }

    /**
     * @param screenX Where the current press is
     */
    private fun switchHeroDirection(screenX: Int) {
        val heroX = (hero.shape.x * (Gdx.graphics.width / WORLD_WIDTH)).roundToInt()

        hero.direction = when {
            between(screenX, heroX - 50, heroX + 50) -> STILL
            screenX < heroX -> LEFT
            screenX > heroX -> RIGHT
            else -> STILL
        }
    }

    private fun between(compare: Int, min: Int, max: Int): Boolean {
        return compare in (min + 1)..(max - 1)
    }
}