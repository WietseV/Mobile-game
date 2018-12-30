package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.GameState
import be.ucll.dirkfalls.service.HighscoreEntry
import be.ucll.dirkfalls.service.HighscoreService
import be.ucll.dirkfalls.utils.AsyncHandler
import be.ucll.dirkfalls.utils.logger
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.*


class GameOverScreen(private val backgroundImage: Texture,
                     private val score: Int,
                     private val game: Game,
                     private val gyro: Boolean) : Screen {
    private val logger = be.ucll.dirkfalls.utils.logger<GameOverScreen>()
    private val stage = Stage()
    private val skin = Skin(Gdx.files.internal("UI/skin.json"))
    private val highscoreService = HighscoreService()

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
               highscoreService.create(nameField.text, score, object: AsyncHandler<HighscoreEntry> {
                   override fun success(data: HighscoreEntry) {
                       submitButton.isDisabled = true
                       submitButton.touchable = Touchable.disabled
                   }

                   override fun error(t: Throwable) {
                        logger.error(t.toString(), t)
                   }
               })
                return false
            }
        })
        val tryAgainButton = TextButton("Try Again", skin)
        tryAgainButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                game.screen = GameScreen(game, GameState(gyro))
                return false
            }
        })
        val mainMenuButton = TextButton("Main menu", skin)
        mainMenuButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                game.screen = HomeScreen(game, GameState(gyro))
                return false
            }
        })

        val shareToFbButton = TextButton("Share to facebook", skin)
        shareToFbButton.addListener(object  : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                (game as DirkFallsGame).fb.share(score)
                return false
            }
        })

        val loadingLabel = Label("Loading Highscores...", skin)

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
        table.row()
        table.add(shareToFbButton)
        table.row()
        table.add(loadingLabel)
        stage.addActor(table)

        highscoreService.all(object : AsyncHandler<List<HighscoreEntry>> {
            override fun success(data: List<HighscoreEntry>) {
                Gdx.app.postRunnable {
                    table.getCell(loadingLabel).setActor(Label("Highscores:", skin))
                    table.row()
                    data.take(10).forEach { entry ->
                        table.add(Label(entry.name, skin))
                        table.add(Label(entry.score.toString(), skin))
                        table.row()
                    }
                }
            }

            override fun error(t: Throwable) {
                logger<DirkFallsGame>().error(t.toString(), t)
            }
        })
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