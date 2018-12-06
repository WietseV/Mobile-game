package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.GameConfig.WORLD_HEIGHT
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.GameState
import be.ucll.dirkfalls.rules.LevelManager
import be.ucll.dirkfalls.screen.buttons.ButtonTouchAdapter
import be.ucll.dirkfalls.screen.buttons.HomeButton
import be.ucll.dirkfalls.screen.buttons.ResetButton
import be.ucll.dirkfalls.utils.rect
import be.ucll.dirkfalls.entities.HeroDirection
import be.ucll.dirkfalls.screen.buttons.Button
import be.ucll.dirkfalls.utils.between
import be.ucll.dirkfalls.utils.scale
import be.ucll.dirkfalls.utils.use
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport

class GameScreen(private val dirkFallsGame: DirkFallsGame, private val gameState: GameState) : DirkScreen() {
    private val camera = OrthographicCamera()
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

    private val gyroscopeAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)



    override fun hide() {
    }

    override fun show() {
        Gdx.input.inputProcessor = GameTouchAdapter(gameState)
        val reset = ResetButton(this)
        val homeButton = HomeButton(this)
        val buttonWidth = getBoxWidthBasedOnScreen(0.35f)
        val buttonHeight = getBoxHeightBasedOnScreen(0.08f)
        val resetCoords = getBoxCoordsOnScreen(0.5f,0.49f, buttonWidth, buttonHeight)
        reset.set(resetCoords.x, resetCoords.y, buttonWidth, buttonHeight)
        homeButton.set(resetCoords.x, resetCoords.y-getBoxHeightBasedOnScreen(0.1f), buttonWidth, buttonHeight)
        buttons.add(reset)
        buttons.add(homeButton)
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
            drawGameOver()
        }

        drawScore(gameState.score)
    }

    private fun renderGameObjects() {
        batch.projectionMatrix = camera.combined
         batch.use {
             gameState.entities.forEach{
                 batch.draw(it.image, it.position.x, it.position.y,  it.size(), it.size())
             }
         }
    }

    private fun renderBackground(){
        batch.projectionMatrix = camera.combined
        var background = gameState.imgBackground

        batch.use{
            batch.draw(background, 0f, 0f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT)
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
        batch.use {
            val char = score.toString()
            font.draw(it, "Score: $char", 20f+font.data.scaleX, Gdx.graphics.height-20f)
        }
    }

    private fun drawGameOver() {
        renderer.use {
            renderer.set(ShapeRenderer.ShapeType.Filled)
            buttons.forEach {
                renderer.color = it.color
                renderer.rect(it)
            }
        }
        batch.use {
            font.data.setScale(5f,5f)
            val textWidth = getTextWidthBasedOnScreen(0.33f)
            val textHeight = getTextHeightBasedOnScreen(0.07f)
            val textCoords = getTextCoordsOnScreen(0.5f,0.535f, textWidth, textHeight)
            val titleWidth = getTextWidthBasedOnScreen(0.6f)
            val titleHeight = getTextHeightBasedOnScreen(0.12f)
            val titleCoords = getTextCoordsOnScreen(0.5f, 0.67f, titleWidth, titleHeight)
            font.draw(batch, "Game over!", titleCoords.x, titleCoords.y, titleWidth, 1, false)
            font.data.setScale(3f, 3f)
            font.draw(batch, "Try again", textCoords.x, textCoords.y, textWidth, 1, false)
            font.draw(batch, "Main menu", textCoords.x, textCoords.y-getTextHeightBasedOnScreen(0.1f), textWidth, 1, false)
        }
    }

    override fun touchUp(x: Float, y: Float) {
        buttons.forEach {
            if (it.contains(x, y)) {
                it.pressButton(dirkFallsGame, HomeScreen(dirkFallsGame, gameState))
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
}


