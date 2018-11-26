package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.GameConfig
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
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

class GameScreen : Screen {
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    private val renderer = ShapeRenderer()
    private val batch = SpriteBatch(5)
    private val font = BitmapFont().apply {
        color = Color.WHITE
    }
    private val hero = Hero(Vector2(WORLD_WIDTH / 2f, 1f))
    private val healthBar = HealthBar(Vector2(WORLD_WIDTH - 2f, WORLD_HEIGHT - 0.4f))
    private val performance = PerformanceLogger()
    private var paused: Boolean = false
    private val entities = mutableListOf<Entity>(hero)
    private var cometTimer = 0f
    private var score = 0


    override fun hide() {
    }

    override fun show() {
        Gdx.input.inputProcessor = GameTouchAdapter(hero)
    }

    override fun render(delta: Float) {
        performance.update(delta)

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (!paused) {
            update(delta)
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
            entities.forEach { it.drawDebug(renderer) }
            healthBar.draw(renderer)
            renderer.setColor(255f, 255f, 255f, 100f)
        }

        drawScore(score)
    }


    private fun update(delta: Float) {
        updateHero(delta)
        updateComet(delta)
        updateHealth()
        createComet(delta)
    }

    private fun updateHealth() {
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

    private fun updateHero(delta: Float) {
        if (hero.outOfBounds(delta)) {
            hero.direction = HeroDirection.STILL
        } else {
            hero.update(delta)
        }
    }

    private fun updateComet(delta: Float) {
        val entRemove = mutableListOf<Comet>()
        entities.filterIsInstance<Comet>().forEach {
            if (it.overlaps(hero)) {
                entRemove.add(it)
                hero.hit()
            }
            if (it.position.y + 1f < 2f) { // Comet destroyed after hitting ground (set to 0f for destruction out of bounds)
                entRemove.add(it)
                score++
            }

            renderer.use { it.update(delta) }
        }
        entities.removeAll(entRemove)
    }

    private fun createComet(delta: Float) {
        cometTimer += delta

        if (cometTimer >= GameConfig.COMET_SPAWN_TIME) {
            cometTimer = 0f // reset timer

            val cometRadius = MathUtils.random(0.1f, 0.3f)
            val cometX = MathUtils.random(0f + cometRadius, GameConfig.WORLD_WIDTH - cometRadius)
            val vector2 = Vector2(cometX, GameConfig.WORLD_HEIGHT + cometRadius)
            val comet = Comet(vector2, cometRadius)
            if (checkCometSpawn(comet)) {
                entities.add(comet)
            }
        }
    }

    private fun checkCometSpawn(comet: Comet): Boolean {
        entities.forEach {
            if (it.overlaps(comet)) {
                return false
            }
        }
        return true
    }

    private fun drawScore(score: Int) {
        batch.use {
            val char = score.toString()
            font.draw(it, char, WORLD_WIDTH / 2, WORLD_HEIGHT / 2)
        }
    }
}
