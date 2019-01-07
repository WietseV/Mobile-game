package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.GameState
import be.ucll.dirkfalls.screen.buttons.Button
import be.ucll.dirkfalls.screen.buttons.ButtonTouchAdapter
import be.ucll.dirkfalls.screen.buttons.OptionsButton
import be.ucll.dirkfalls.screen.buttons.PlayButton
import be.ucll.dirkfalls.utils.rect
import be.ucll.dirkfalls.utils.use
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport

class HomeScreen(private val dirkFallsGame: Game, gameState: GameState) :
        DirkScreen(gameState) {
    private val camera = OrthographicCamera()
    private val aspectRatio = Gdx.graphics.height / Gdx.graphics.width
    private val textCamera = OrthographicCamera(10000f, 10000f * aspectRatio)

    private val renderer = ShapeRenderer()
    private val spriteBatch = SpriteBatch()
    private val font = BitmapFont()
    private val buttons = mutableListOf<Button>()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private val gyroscopeAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)
    private val vibratorAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Vibrator)

    override fun hide() {
    }

    override fun show() {
        println("Width: ${Gdx.graphics.width}, Height: ${Gdx.graphics.height}")
        Gdx.input.inputProcessor = ButtonTouchAdapter(this)
        gameState.useGyro = gyroscopeAvail
        gameState.useVibration = vibratorAvail

        val play = PlayButton(this)
        val options = OptionsButton(this)
        gameState.intro()
        val buttonWidth = getBoxWidthBasedOnScreen(0.35f)
        val buttonHeight = getBoxHeightBasedOnScreen(0.08f)
        val buttonCoords = getBoxCoordsOnScreen(0.5f, 0.49f, buttonWidth, buttonHeight)
        play.set(buttonCoords.x, buttonCoords.y, buttonWidth, buttonHeight)
        options.set(buttonCoords.x, buttonCoords.y - getBoxHeightBasedOnScreen(0.1f), buttonWidth, buttonHeight)
        buttons.add(play)
        buttons.add(options)
        font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
    }

    override fun touchUp(x: Float, y: Float) {
        buttons.forEach {
            if (it.contains(x, y)) {
                if (it is PlayButton) {
                    it.pressButton(dirkFallsGame, GameScreen(dirkFallsGame, gameState))
                } else if (it is OptionsButton) {
                    it.pressButton(dirkFallsGame, OptionsScreen(gameState, dirkFallsGame, this))
                }
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
        gameState.assetManager.update()
        //performance.update(delta)

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        gameState.updateLevels(delta)
        renderer.projectionMatrix = camera.combined
        renderer.use {
            //gameState.entities.forEach { it.drawDebug(renderer) }
            renderer.setAutoShapeType(true)
            renderer.set(ShapeRenderer.ShapeType.Filled)
        }

        drawBackground()
        drawButtons()
        drawText()

    }

    private fun drawBackground() {
        if (gameState.backgroundLoaded) {
            spriteBatch.projectionMatrix = camera.combined

            val background = gameState.levelBackground

            spriteBatch.use {
                spriteBatch.draw(background, 0f, 0f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT)

                gameState.entities.forEach { entity ->
                    spriteBatch.draw(entity.image, entity.position.x, entity.position.y, entity.size(), entity.size())
                }
            }
        }
    }


    private fun drawButtons() {
        renderer.use {
            renderer.set(ShapeRenderer.ShapeType.Filled)
            buttons.forEach {
                renderer.color = it.color
                renderer.rect(it)
            }
        }


    }

    private fun drawText() {
        spriteBatch.projectionMatrix = textCamera.combined
        spriteBatch.use {
            font.data.setScale(80f)

            font.draw(spriteBatch, "DIRK FALLS", 0f, 2000f * aspectRatio, 0f, 1, false)

            font.data.setScale(30f)

            font.draw(spriteBatch, "Play", 0f, 100f * aspectRatio, 0f, 1, false)
            font.draw(spriteBatch, "Options", 0f, -900f * aspectRatio, 0f, 1, false)
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