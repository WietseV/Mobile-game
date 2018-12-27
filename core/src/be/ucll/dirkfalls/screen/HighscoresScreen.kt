package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.GameState
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*


class GameOverScreen(private val backgroundImage: Texture,
                     private val score: Int,
                     private val game: Game) : Screen {
    private val stage = Stage()
    private val skin = Skin(Gdx.files.internal("UI/skin.json"))
    override fun hide() {
    }

    override fun show() {
        Gdx.input.inputProcessor = stage

        val scoreLabel = Label("Your score was $score", skin)

        val nameLabel = Label("Your name:", skin)
        val nameField = TextField("", skin)
        val submitButton = TextButton("Submit", skin)
        submitButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                println(nameField.text)
                return true
            }
        })
        val tryAgainButton = TextButton("Try Again", skin)
        tryAgainButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                game.screen = GameScreen(game, GameState())
                return false
            }
        })
        val mainMenuButton = TextButton("Main menu", skin)
        mainMenuButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                game.screen = HomeScreen(game, GameState())
                return false
            }
        })


        val table = Table()
        table.setSize(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        table.setPosition(0f, 0f)


        table.add(scoreLabel)
        table.row()
        table.add(nameLabel)
        table.add(nameField)
        table.row()
        table.add(submitButton)
        table.row()
        table.add(tryAgainButton)
        table.add(mainMenuButton)

        stage.addActor(table)

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        val batch = stage.batch
        batch.begin()
        batch.draw(backgroundImage, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        batch.end()

        stage.act(Math.min(Gdx.graphics.deltaTime, 1 / 30f))
        stage.draw()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.dispose()
        skin.dispose()
    }

}