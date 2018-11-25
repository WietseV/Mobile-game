package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.entities.Hero
import be.ucll.dirkfalls.entities.HeroDirection
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter

internal class GameTouchAdapter(private val hero: Hero) : InputAdapter() {
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val x = screenX * 1f / Gdx.graphics.width
        hero.direction = when {
            x * GameConfig.WORLD_WIDTH < GameConfig.WORLD_WIDTH / 2 -> HeroDirection.LEFT
            x * GameConfig.WORLD_WIDTH > GameConfig.WORLD_WIDTH / 2 -> HeroDirection.RIGHT
            else -> HeroDirection.STILL
        }
        return super.touchDown(screenX, screenY, pointer, button)
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        hero.direction = HeroDirection.STILL
        return super.touchUp(screenX, screenY, pointer, button)
    }
}