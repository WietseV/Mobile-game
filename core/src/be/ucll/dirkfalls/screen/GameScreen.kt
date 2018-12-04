package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.GameConfig.WORLD_HEIGHT
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.GameState
import be.ucll.dirkfalls.rules.LevelManager
import be.ucll.dirkfalls.screen.buttons.ButtonTouchAdapter
import be.ucll.dirkfalls.screen.buttons.ResetButton
import be.ucll.dirkfalls.utils.rect
import be.ucll.dirkfalls.entities.HeroDirection
import be.ucll.dirkfalls.utils.between
import be.ucll.dirkfalls.utils.use
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport

class GameScreen(val dirkFallsGame: DirkFallsGame) : DirkScreen() {
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    private val renderer = ShapeRenderer()
    private val batch = SpriteBatch(5)
    private val font = BitmapFont().apply {
        color = Color.WHITE
    }
    private var gameState = GameState()
    private val healthBar = HealthBar(Vector2(WORLD_WIDTH - 2f, WORLD_HEIGHT - 0.4f))
    private val performance = PerformanceLogger()
    private var paused: Boolean = false
    private val reset = ResetButton(this)


    override fun hide() {
    }

    override fun show() {
        Gdx.input.inputProcessor = GameTouchAdapter(gameState)
        reset.set(WORLD_WIDTH/2f-1f, WORLD_HEIGHT/2f-0.45f, 2f, 0.75f)
        font.data.setScale(3f, 3f)
        font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
    }

    override fun render(delta: Float) {
        performance.update(delta)

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (!paused) {
            gameState.updateLevels(delta)
            updateHealth()
        }

        val hero = gameState.hero
        val pressed = gameState.pressedPosition
        if(pressed != null) {
            hero.direction = when {
                between(pressed.x, hero.position.x, hero.position.x + hero.shape.width) -> HeroDirection.STILL
                pressed.x < hero.position.x -> HeroDirection.LEFT
                pressed.x > hero.position.x -> HeroDirection.RIGHT
                else -> HeroDirection.STILL
            }
        } else {
            hero.direction = HeroDirection.STILL
        }


        renderer.projectionMatrix = camera.combined
        renderer.use {
            renderer.setAutoShapeType(true)
            renderer.set(ShapeRenderer.ShapeType.Filled)
            renderer.setColor(gameState.background.color)
            renderer.rect(gameState.background.background)
            gameState.entities.forEach { it.drawDebug(renderer) }
            healthBar.draw(renderer)
            renderer.setColor(0f, 1f, 0f, 1f)
            renderer.rect(0f, 0f, WORLD_WIDTH, 1f)
            renderer.setColor(255f, 255f, 255f, 100f)
        }

        if (gameState.gameOver) {
            drawGameOver()
        }

        drawScore(gameState.score)
    }

    private fun updateHealth() {
        val hero = gameState.hero

        renderer.use { healthBar.update(hero.health) }
        if (hero.health == 0) {
            Gdx.input.inputProcessor = ButtonTouchAdapter(this)
            gameState.gameOver = true
            //dirkFallsGame.screen = KillScreen(dirkFallsGame)
            pause()
        }
    }

    override fun pause() {
        paused = true
    }

    override fun resume() {
        paused = false
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun dispose() {
        renderer.dispose()
        batch.dispose()
        font.dispose()
    }

    private fun drawScore(score: Int) {
        batch.use {
            val char = score.toString()
            font.draw(it, "Score: $char", 20f+font.data.scaleX, Gdx.graphics.height-20f)
        }
    }

    private fun drawGameOver() {
        renderer.use {
            renderer.color = reset.color
            renderer.set(ShapeRenderer.ShapeType.Filled)
            renderer.rect(reset.x,reset.y,reset.width,reset.height)
        }
        batch.use {
            font.data.setScale(5f,5f)
            font.draw(batch, "Game over!", Gdx.graphics.width/3f-1f, 3f*Gdx.graphics.height/5f)
            font.data.setScale(3f, 3f)
            font.draw(batch, "Try again", Gdx.graphics.width/2f-10f, Gdx.graphics.height/2f, 20f, 1, false)
        }
    }

    override fun touchUp(x: Float, y: Float) {
        if (reset.contains(x, y)) {
            reset.pressButton(dirkFallsGame)
        }
    }

    override fun touchDown(x: Float, y: Float) {
        if (reset.contains(x, y)) {
            reset.touchButton()
        } else {
            reset.touchMovedOff()
        }
    }

    override fun touchDragged(x: Float, y: Float) {
        if (!reset.contains(x, y)) {
            reset.touchMovedOff()
        } else {
            reset.touchButton()
        }
    }

    fun resetGame() {
        gameState.resetGame()
        Gdx.input.inputProcessor = GameTouchAdapter(gameState)
        resume()
    }
}


