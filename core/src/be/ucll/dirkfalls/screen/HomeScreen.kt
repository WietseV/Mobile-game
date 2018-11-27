package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.GameConfig.WORLD_HEIGHT
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.screen.buttons.*
import be.ucll.dirkfalls.utils.use
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport

class HomeScreen(val dirkFallsGame: DirkFallsGame) : DirkScreen() {
    private val camera = OrthographicCamera()
    private val performance = PerformanceLogger()
    private val renderer = ShapeRenderer()
    private val spriteBatch = SpriteBatch()
    private val font = BitmapFont()
    private val buttons = mutableListOf<Button>()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)

    override fun hide() {
    }

    override fun show() {
        Gdx.input.inputProcessor = ButtonTouchAdapter(this)
        val play = PlayButton()
        play.set(WORLD_WIDTH/2f-1f, WORLD_HEIGHT/2f-0.45f, 2f, 0.75f)
        buttons.add(play)
    }

    override fun screenPressed(x: Float, y: Float) {
        buttons.forEach {
            if (it.contains(x, y)) {
                dirkFallsGame.screen = GameScreen(dirkFallsGame)
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
            renderer.color = Color.RED
            renderer.set(ShapeRenderer.ShapeType.Filled)
            buttons.forEach { renderer.rect(it.x,it.y,it.width,it.height) }
        }
    }

    private fun drawText() {
        spriteBatch.use {
            font.draw(spriteBatch, "Start game!", Gdx.graphics.width/2f-10f, Gdx.graphics.height/2f, 20f, 1, false)
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