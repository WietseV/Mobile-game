package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.GameConfig.WORLD_HEIGHT
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.GameState
import be.ucll.dirkfalls.screen.buttons.*
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

class GameScreen(private val game: Game, gameState: GameState) : DirkScreen(gameState) {
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
    private var paused: Boolean = false
    private val buttons = mutableListOf<Button>()

    //private val gyroscopeAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)

    //pause button
    val pauseButton = PauseButton(this)
    var pButtonWidth = 0.08f
    val pauseButtonWidth = getBoxWidthBasedOnScreen(pButtonWidth)
    val pauseButtonHeight = getHeightBasedOnWidth(pButtonWidth)

    var top = 1f
    var topw = 0f

    var coordbox = getBoxCoordsOnScreen(topw, top, 0f, pauseButtonHeight * 2)


    override fun hide() {
    }


    override fun show() {
        Gdx.input.inputProcessor = GameTouchAdapter(gameState)
        font.data.setScale(3f, 3f)
        font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        //pause button
        var pauseButton = PauseButton(this)
        var pButtonWidth = 0.05f
        val pauseButtonWidth = getBoxWidthBasedOnScreen(pButtonWidth)
        val pauseButtonHeight = getHeightBasedOnWidth(pButtonWidth)

        var top = 1f
        var topw = 0f

        var coordbox = getBoxCoordsOnScreen(top, topw, pauseButtonWidth, pauseButtonHeight)

        pauseButton.set(coordbox.x, coordbox.y, pauseButtonWidth, pauseButtonHeight)
        gameState.setPauseButton(pauseButton)

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
        /* val background = Texture("../android/assets/backgrounds/backgroundLevel1.jpeg")
        batch.use {
             batch.draw(background, GameConfig.WORLD_HEIGHT, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, GameConfig.WORLD_WIDTH)}
         */
        renderBackground()
        renderer.use {
            renderer.setAutoShapeType(true)
            renderer.set(ShapeRenderer.ShapeType.Filled)
            /*renderer.color = gameState.background.color
            renderer.rect(gameState.background.background)*/
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
            gameState.entities.forEach {
                batch.draw(it.image, it.position.x, it.position.y, it.size(), it.size())
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
        game.screen = GameOverScreen(gameState.levelBackground, gameState.score, game)
    }

    override fun touchUp(x: Float, y: Float) {
        buttons.forEach {
            if (it.contains(x, y)) {
                it.pressButton(game, HomeScreen(game, gameState))
            }
        }
    }

    override fun touchDown(x: Float, y: Float) {
        buttons.forEach {
            if (it.contains(x, y)) {
                it.touchButton()
            } else {
                it.touchMovedOff()
            }
        }
    }

    override fun touchDragged(x: Float, y: Float) {
        buttons.forEach {
            if (!it.contains(x, y)) {
                it.touchMovedOff()
            } else {
                it.touchButton()
            }
        }
    }

    fun resetGame() {
        gameState.resetGame()
        Gdx.input.inputProcessor = GameTouchAdapter(gameState)
        resume()
    }

    fun drawPauseButton() {
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

        //buttons.add(pauseButton)
    }
}


