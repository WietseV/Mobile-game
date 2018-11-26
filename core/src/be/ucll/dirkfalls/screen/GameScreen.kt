package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.GameConfig.WORLD_HEIGHT
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.entities.*
import be.ucll.dirkfalls.utils.use
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport

class GameScreen : Screen {
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    private val renderer = ShapeRenderer()
    private val batch = SpriteBatch(5)
    private val font = BitmapFont().apply {
        color = Color.WHITE
    }

    private val healthBar = HealthBar(Vector2(WORLD_WIDTH - 2f, WORLD_HEIGHT - 0.4f))
    private val performance = PerformanceLogger()
    private val entityManager: EntityManager = EntityManagerImpl()
    private var paused: Boolean = false
    private var score = 0


    override fun hide() {
    }

    override fun show() {
        Gdx.input.inputProcessor = GameTouchAdapter(entityManager.hero)
    }

    override fun render(delta: Float) {
        performance.update(delta)

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (!paused) {
            entityManager.update(delta)
            updateHealth()
        }

        renderer.projectionMatrix = camera.combined
        renderer.use {
            renderer.setAutoShapeType(true)
            renderer.set(ShapeRenderer.ShapeType.Filled)
            renderer.setColor(0f, 0f, 255f, 100f)
            renderer.rect(0f, 1f, WORLD_WIDTH, WORLD_HEIGHT)
            renderer.setColor(0f, 255f, 0f, 100f)
            renderer.rect(0f, 0f, WORLD_WIDTH, 1f)
            renderer.setColor(255f, 255f, 255f, 100f)
            entityManager.draw(renderer)
            healthBar.draw(renderer)
            renderer.setColor(255f, 255f, 255f, 100f)
        }

        drawScore(score)
    }

    private fun updateHealth() {
        val hero = entityManager.hero

        renderer.use { healthBar.update(hero.health) }
        if (hero.health == 0) {
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
            font.draw(it, char, WORLD_WIDTH / 2, WORLD_HEIGHT / 2)
        }
    }
}
