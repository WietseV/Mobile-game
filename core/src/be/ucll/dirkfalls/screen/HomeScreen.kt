package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.utils.use
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport

class HomeScreen: Screen {

    private val camera = OrthographicCamera()
    private val performance = PerformanceLogger()
    private val renderer = ShapeRenderer()
    private var paused: Boolean = false

    private val spriteBatch = SpriteBatch()
    private val  font = BitmapFont()



    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)

    override fun hide() {
    }

    override fun show() {
        Gdx.input.inputProcessor = InputAdapter()
    }

    override fun render(delta: Float) {
        performance.update(delta)

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
            renderer.setColor(255f, 255f, 255f, 100f)
        }

        spriteBatch.begin()
        font.draw(spriteBatch, "Start game!", Gdx.graphics.width/2f-20f, Gdx.graphics.height/2f, 20f, 1, false)
        spriteBatch.end()
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
    }
}