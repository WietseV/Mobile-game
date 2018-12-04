package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.GameConfig.WORLD_HEIGHT
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.GameState
import be.ucll.dirkfalls.screen.buttons.*
import be.ucll.dirkfalls.utils.rect
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
import com.badlogic.gdx.utils.viewport.FitViewport

class HomeScreen(private val dirkFallsGame: DirkFallsGame, val gameState: GameState = GameState()) : DirkScreen() {
    private val camera = OrthographicCamera()
    private val performance = PerformanceLogger()
    private val renderer = ShapeRenderer()
    private val spriteBatch = SpriteBatch()
    private val font = BitmapFont()
    private val buttons = mutableListOf<Button>()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private val gyroscopeAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)


    override fun hide() {
    }

    override fun show() {
        Gdx.input.inputProcessor = ButtonTouchAdapter(this)
        val color: Color
        if (gyroscopeAvail) {
            color = Color.RED
            gameState.useGyro = true
        } else {
            color = Color.GRAY
            gameState.useGyro = false
        }
        val play = PlayButton(this)
        val gyro = UseGyroButton(this, color)
        val buttonWidth = getBoxWidthBasedOnScreen(0.35f)
        val buttonHeight = getBoxHeightBasedOnScreen(0.08f)
        val buttonCoords = getBoxCoordsOnScreen(0.5f,0.49f, buttonWidth, buttonHeight)
        play.set(buttonCoords.x, buttonCoords.y, buttonWidth, buttonHeight)
        gyro.set(buttonCoords.x, buttonCoords.y-getBoxHeightBasedOnScreen(0.1f), buttonWidth, buttonHeight)
        buttons.add(play)
        buttons.add(gyro)
        font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

    }

    override fun touchUp(x: Float, y: Float) {
        buttons.forEach {
            if (it.contains(x, y)) {
                gameState.resetGame()
                it.pressButton(dirkFallsGame, GameScreen(dirkFallsGame, gameState))
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

    override fun render(delta: Float) {
        //performance.update(delta)

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.projectionMatrix = camera.combined
        renderer.use {
            renderer.setAutoShapeType(true)
            renderer.set(ShapeRenderer.ShapeType.Filled)
            renderer.setColor(0f, 0f, 255f, 100f)
            renderer.rect(0f, 1f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT)
            renderer.setColor(0f, 255f, 0f, 100f)
            renderer.rect(0f, 0f, GameConfig.WORLD_WIDTH, 1f)
            renderer.setColor(255f, 255f, 255f, 100f)

        }

        drawButtons()
        drawText()
    }
    private fun drawButtons() {
        renderer.use {
            renderer.set(ShapeRenderer.ShapeType.Filled)
            buttons.forEach {
                renderer.color = it.color
                renderer.rect(it) }
        }
    }

    private fun drawText() {
        spriteBatch.use {
            val textWidth = getTextWidthBasedOnScreen(0.33f)
            val textHeight = getTextHeightBasedOnScreen(0.07f)
            val textCoords = getTextCoordsOnScreen(0.5f,0.535f, textWidth, textHeight)
            val titleWidth = getTextWidthBasedOnScreen(0.8f)
            val titleHeight = getTextHeightBasedOnScreen(0.12f)
            val titleCoords = getTextCoordsOnScreen(0.5f, 0.7f, titleWidth, titleHeight)
            font.data.setScale(8f, 8f)
            font.draw(spriteBatch, "DIRK FALLS", titleCoords.x, titleCoords.y, titleWidth, 1, false)
            font.data.setScale(3f, 3f)
            font.draw(spriteBatch, "Start game!", textCoords.x, textCoords.y, textWidth, 1, false)
            font.data.setScale(2.5f, 2.5f)
            val gyro = when (gameState.useGyro) {
                true -> "V"
                else -> "X"
            }
            font.draw(spriteBatch, "Use gyroscope? ($gyro)", textCoords.x, textCoords.y-getTextHeightBasedOnScreen(0.1f), textWidth, 1, false)
        }
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun dispose() {
        renderer.dispose()
    }
}