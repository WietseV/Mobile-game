package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.GameConfig.WORLD_HEIGHT
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.GameState
import be.ucll.dirkfalls.rules.RuleManager
import be.ucll.dirkfalls.screen.buttons.ButtonTouchAdapter
import be.ucll.dirkfalls.screen.buttons.ResetButton
import be.ucll.dirkfalls.utils.use
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
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
    private val ruleManager = RuleManager(gameState)
    private var paused: Boolean = false
    private val reset = ResetButton()



    override fun hide() {
    }

    override fun show() {
        Gdx.input.inputProcessor = GameTouchAdapter(gameState.hero)
        reset.set(WORLD_WIDTH/2f-1f, WORLD_HEIGHT/2f-0.45f, 2f, 0.75f)
    }

    override fun render(delta: Float) {
        performance.update(delta)

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (!paused) {
            ruleManager.update(delta)
            updateHealth()
        }

        renderer.projectionMatrix = camera.combined
        renderer.use {
            renderer.setAutoShapeType(true)
            renderer.set(ShapeRenderer.ShapeType.Filled)
            renderer.setColor(0f, 0f, 1f, 1f)
            renderer.rect(0f, 1f, WORLD_WIDTH, WORLD_HEIGHT)
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
            font.data.setScale(3f, 3f)
            font.draw(it, "Score: $char", 20f+font.data.scaleX, Gdx.graphics.height-20f)
        }
    }

    private fun drawGameOver() {
        renderer.use {
            renderer.color = Color.RED
            renderer.set(ShapeRenderer.ShapeType.Filled)
            renderer.rect(reset.x,reset.y,reset.width,reset.height)
        }
        batch.use {
            font.data.setScale(1f, 1f)
            font.draw(batch, "Reset game?", Gdx.graphics.width/2f-10f, Gdx.graphics.height/2f, 20f, 1, false)
        }
    }

    override fun screenPressed(x: Float, y: Float) {
        if (reset.contains(x, y)) {
            resetGame()
        }
    }

    private fun resetGame() {
        gameState.resetGame()
        Gdx.input.inputProcessor = GameTouchAdapter(gameState.hero)
        resume()
    }
}
