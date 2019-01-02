package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.GameConfig.WORLD_HEIGHT
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.GameState
import be.ucll.dirkfalls.screen.buttons.ButtonTouchAdapter
import be.ucll.dirkfalls.screen.buttons.PauseButton
import be.ucll.dirkfalls.utils.rect
import be.ucll.dirkfalls.utils.use
import com.badlogic.gdx.Game
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

class GameScreen(val game: Game, gameState: GameState) : DirkScreen(gameState) {
    private val camera = OrthographicCamera()
    private val aspectRatio = Gdx.graphics.height / Gdx.graphics.width
    private val textCamera = OrthographicCamera(10000f, 10000f * aspectRatio)
    private val viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    private val renderer = ShapeRenderer()
    private val batch = SpriteBatch(5)
    private val font = BitmapFont().apply {
        color = Color.WHITE
    }
    private val healthBar = HealthBar(Vector2(WORLD_WIDTH - 2f, WORLD_HEIGHT - 0.4f))
    private val performance = PerformanceLogger()
    var paused: Boolean = false

    //pause button
    private val pauseButton = PauseButton(this)
    private var pButtonWidth = 0.08f
    private val pauseButtonWidth = getBoxWidthBasedOnScreen(pButtonWidth)
    private val pauseButtonHeight = getHeightBasedOnWidth(pButtonWidth)

    private var top = 1f
    private var topw = 0f

    private var coordbox = getBoxCoordsOnScreen(topw, top, 0f, pauseButtonHeight * 2)


    override fun hide() {
    }


    override fun show() {
        pauseButton.set(coordbox.x, coordbox.y, pauseButtonWidth, pauseButtonHeight)

        Gdx.input.inputProcessor = GameTouchAdapter(gameState, pauseButton)
        font.data.setScale(3f, 3f)
        font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
    }

    override fun render(delta: Float) {
        gameState.assetManager.update()
        performance.update(delta)

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (!paused) {
            gameState.updateLevels(delta)
            updateHealth()
        }
        renderer.projectionMatrix = camera.combined
        renderBackground()
        renderer.use {
            renderer.setAutoShapeType(true)
            renderer.set(ShapeRenderer.ShapeType.Filled)
            gameState.entities.forEach { it.drawDebug(renderer) }
            healthBar.draw(renderer)
            renderer.setColor(0f, 1f, 0f, 1f)
            renderer.rect(0f, 0f, WORLD_WIDTH, 1f)
            renderer.setColor(255f, 255f, 255f, 100f)

        }
        renderGameObjects()
        if (gameState.gameOver) {
            gameOver()
        }
        drawPauseButton()

        drawScore(gameState.score)
    }

    private fun renderGameObjects() {
        batch.projectionMatrix = camera.combined
        batch.use {
            gameState.entities.forEach { entity ->
                batch.draw(entity.image, entity.position.x, entity.position.y, entity.size(), entity.size())
            }
        }
    }

    private fun renderBackground() {
        if (gameState.backgroundLoaded) {
            batch.projectionMatrix = camera.combined
            val background = gameState.levelBackground

            batch.use {
                batch.draw(background, 0f, 0f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT)
            }
        }
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
        batch.projectionMatrix = textCamera.combined
        batch.use {
            font.data.setScale(30f)
            val char = score.toString()
            font.draw(it, "Score: $char", 10f, 4500f * aspectRatio, 0f, 1, false)
        }
    }

    private fun gameOver() {
        game.screen = GameOverScreen(gameState.levelBackground, gameState.score, game, gameState.useGyro)
    }

    private fun drawPauseButton() {
        //pause button

        // rare berekening, geen idee hoe dit werkt, maths!
        pauseButton.set(coordbox.x, coordbox.y, pauseButtonWidth, pauseButtonHeight)

        renderer.use {
            renderer.rect(pauseButton)
        }

        batch.projectionMatrix = camera.combined
        batch.use {
            batch.draw(pauseButton.pauseDrawable, pauseButton.x, pauseButton.y, pauseButtonWidth, pauseButtonHeight)
        }
    }

    override fun touchDown(x: Float, y: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun touchUp(x: Float, y: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun touchDragged(x: Float, y: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


