package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.DirkFallsGame
import be.ucll.dirkfalls.GameState
import be.ucll.dirkfalls.service.HighscoreEntry
import be.ucll.dirkfalls.service.HighscoreService
import be.ucll.dirkfalls.utils.AsyncHandler
import be.ucll.dirkfalls.utils.logger
import be.ucll.dirkfalls.utils.scale
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener


class GameOverScreen(val gameState: GameState, private val game: Game) : Screen {
    private val logger = be.ucll.dirkfalls.utils.logger<GameOverScreen>()
    private val stage = Stage()
    private val skin = Skin(Gdx.files.internal("UI/skin.json"))
    private val highscoreService = HighscoreService()
    private val backgroundImage: Texture = gameState.levelBackground
    private val score: Int = gameState.score
    private val aspectRatio = scale(Gdx.graphics.width * 1f, 0f, 2000f, 0f, 4f)

    override fun hide() {
    }

    override fun show() {
        Gdx.input.inputProcessor = stage

        val scoreLabel = Label("Your score was $score", skin)

        val nameLabel = Label("Your name:", skin)
        val nameField = TextField("", skin)
        val fieldContainer = Container<TextField>(nameField)
        fieldContainer.isTransform = true
        fieldContainer.setScale(aspectRatio * 0.8f)
        nameField.maxLength = 25
        val emptyLabel = Label("", skin)
        val errorLabel = Label("Name can't be empty!", skin)
        val submitButton = TextButton("Submit", skin)
        submitButton.isTransform = true
        submitButton.setScale(aspectRatio * 0.8f)
        val tryAgainButton = TextButton("Try Again", skin)
        tryAgainButton.isTransform = true
        tryAgainButton.setScale(aspectRatio * 0.8f)
        tryAgainButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                stage.unfocusAll()
                Gdx.input.setOnscreenKeyboardVisible(false)
                gameState.resetGame()
                game.screen = GameScreen(game, gameState)
                return false
            }
        })
        val mainMenuButton = TextButton("Main menu", skin)
        mainMenuButton.isTransform = true
        mainMenuButton.setScale(aspectRatio * 0.8f)
        mainMenuButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                stage.unfocusAll()
                Gdx.input.setOnscreenKeyboardVisible(false)
                gameState.resetGame()
                game.screen = HomeScreen(game, gameState)
                return false
            }
        })

        val shareToFbButton = TextButton("Share to facebook", skin)
        shareToFbButton.isTransform = true
        shareToFbButton.setScale(aspectRatio * 0.8f)
        shareToFbButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                stage.unfocusAll()
                Gdx.input.setOnscreenKeyboardVisible(false)
                (game as DirkFallsGame).fb.share(score)
                return false
            }
        })

        val loadingLabel = Label("Loading Highscores...", skin)

        val table = Table()
        table.setSize(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        table.setPosition(0f, 0f)

        table.add(scoreLabel).pad(aspectRatio * 5f).colspan(2).center()
        scoreLabel.setFontScale(aspectRatio)
        table.row()
        table.add(nameLabel).pad(aspectRatio * 5f).left()
        nameLabel.setFontScale(aspectRatio)
        table.add(fieldContainer).pad(aspectRatio * 5f).left().bottom()
        table.row()
        table.add(submitButton).pad(aspectRatio * 8f, 0f, aspectRatio * 8f, 0f).colspan(2).left()
        table.row()
        table.add(emptyLabel).pad(aspectRatio * 5f).colspan(2).left()
        emptyLabel.setFontScale(aspectRatio)
        table.row()
        table.add(tryAgainButton).pad(aspectRatio * 8f, 0f, aspectRatio * 8f, 0f).left()
        table.add(mainMenuButton).pad(aspectRatio * 8f, 0f, aspectRatio * 8f, 0f).left()
        table.row()
        table.add(shareToFbButton).pad(aspectRatio * 8f, 0f, aspectRatio * 8f, 0f).colspan(2).left()
        table.row()
        table.add(loadingLabel).pad(aspectRatio * 5f).colspan(2).left()
        loadingLabel.setFontScale(aspectRatio)
        stage.addActor(table)

        submitButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                stage.unfocusAll()
                if (nameField.text.trim().isEmpty()) {
                    errorLabel.setFontScale(aspectRatio)
                    errorLabel.color = Color.RED
                    table.getCell(emptyLabel).setActor(errorLabel)
                } else {
                    highscoreService.create(nameField.text, score, object : AsyncHandler<HighscoreEntry> {
                        override fun success(data: HighscoreEntry) {
                            submitButton.isDisabled = true
                            submitButton.touchable = Touchable.disabled
                            val submitted = Label("Your highscore was submitted successfully!", skin)
                            table.getCell(submitButton).setActor(submitted)
                            submitted.setFontScale(aspectRatio)
                        }

                        override fun error(t: Throwable) {
                            logger.error(t.toString(), t)
                        }
                    })
                    if (table.getCell(errorLabel) != null) {
                        table.getCell(errorLabel).setActor(emptyLabel)
                    }
                }
                return false
            }
        })

        highscoreService.all(object : AsyncHandler<List<HighscoreEntry>> {
            override fun success(data: List<HighscoreEntry>) {
                Gdx.app.postRunnable {
                    val hs = Label("Highscores:", skin)
                    hs.setFontScale(aspectRatio)
                    table.getCell(loadingLabel).setActor(hs)
                    table.row()
                    data.take(10).forEach { entry ->
                        val name: Label = when {
                            entry.name.trim().length > 25 -> Label(entry.name.trim().substring(0, 25), skin)
                            else -> Label(entry.name.trim(), skin)
                        }
                        table.add(name).pad(aspectRatio * 5f).left()
                        name.setFontScale(aspectRatio)
                        val score = Label(entry.score.toString(), skin)
                        table.add(score).pad(aspectRatio * 5f).left()
                        score.setFontScale(aspectRatio)
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