package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.GameState
import be.ucll.dirkfalls.rules.Difficulty
import be.ucll.dirkfalls.utils.scale
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener

class OptionsScreen(gameState: GameState, private val game: Game, private val prevScreen: Screen) : DirkScreen(gameState) {

    private val stage = Stage()
    private val skin = Skin(Gdx.files.internal("UI/skin.json"))
    private val backgroundImage: Texture = gameState.levelBackground
    private val aspectRatio = scale(Gdx.graphics.width * 1f, 0f, 2000f, 1f, 4f)

    private val useGyro: Boolean = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)
    private val useVibration: Boolean = Gdx.input.isPeripheralAvailable(Input.Peripheral.Vibrator)

    override fun hide() {
    }

    override fun show() {
        Gdx.input.inputProcessor = stage

        val musicLabel = Label("Music:", skin)
        val musicVolume = Label(gameState.music.toString(), skin)
        musicLabel.setFontScale(aspectRatio)
        musicVolume.setFontScale(aspectRatio)
        val musicSlider = Slider(0f,100f,1f, false, skin)
        val musicContainer = Container<Slider> (musicSlider)
        musicContainer.isTransform = true
        musicContainer.setScale(aspectRatio)
        musicSlider.value = gameState.music
        musicSlider.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                gameState.music = musicSlider.value
                musicVolume.setText(gameState.music.toString())
            }
        })

        val soundLabel = Label("Sounds:", skin)
        val soundVolume = Label(gameState.sound.toString(), skin)
        soundLabel.setFontScale(aspectRatio)
        soundVolume.setFontScale(aspectRatio)
        val soundSlider = Slider(0f, 100f, 1f, false, skin)
        val soundContainer = Container<Slider> (soundSlider)
        soundContainer.isTransform = true
        soundContainer.setScale(aspectRatio)

        soundSlider.value = gameState.sound
        soundSlider.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                gameState.sound = soundSlider.value
                soundVolume.setText(gameState.sound.toString())
            }
        })

        val gyroscopeLabel = Label("Gyroscope:", skin)
        gyroscopeLabel.setFontScale(aspectRatio)
        val gyro = when (gameState.useGyro) {
            true -> "On"
            false -> "Off"
        }
        println(gameState.useGyro)
        val gyroButton: Actor
        if (useGyro) {
            gyroButton  = TextButton(gyro, skin)
            gyroButton.isTransform = true
            gyroButton.setScale(aspectRatio * 0.8f)
            gyroButton.width = aspectRatio * 10f
            gyroButton.addListener(object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    when (gameState.useGyro) {
                        true -> {
                            gyroButton.setText("Off")
                            gameState.useGyro = false
                        }
                        false -> {
                            gyroButton.setText("On")
                            gameState.useGyro = true
                        }
                    }
                    return false
                }
            })
        } else {
            gyroButton = Label("Unavailable", skin)
            gyroButton.setFontScale(aspectRatio)
        }

        val vibrateLabel = Label("Vibrate: ", skin)
        vibrateLabel.setFontScale(aspectRatio)
        val vibrate = when (gameState.useVibration) {
            true -> "On"
            false -> "Off"
        }
        val vibrateButton: Actor
        if (useVibration) {
            vibrateButton = TextButton(vibrate, skin)
            vibrateButton.isTransform = true
            vibrateButton.setScale(aspectRatio * 0.8f)
            vibrateButton.width = aspectRatio * 10f
            vibrateButton.addListener(object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    when (gameState.useVibration) {
                        true -> {
                            vibrateButton.setText("Off")
                            gameState.useVibration = false
                        }
                        false -> {
                            vibrateButton.setText("On")
                            gameState.useVibration = true
                        }
                    }
                    return false
                }
            })
        } else {
            vibrateButton = Label("Unavailable", skin)
            vibrateButton.setFontScale(aspectRatio)
        }

        val difficultyLabel = Label("Difficulty:", skin)
        difficultyLabel.setFontScale(aspectRatio)
        val diff = when (gameState.difficulty) {
            Difficulty.EASY -> "Easy"
            Difficulty.MEDIUM -> "Medium"
            Difficulty.HARD -> "Hard"
        }
        val difficultyButton = TextButton(diff, skin)
        difficultyButton.isTransform = true
        difficultyButton.setScale(aspectRatio*0.8f)
        difficultyButton.width = aspectRatio*10f
        difficultyButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                when (gameState.difficulty) {
                    Difficulty.EASY -> {
                        gameState.difficulty = Difficulty.MEDIUM
                        difficultyButton.setText("Medium")
                    }
                    Difficulty.MEDIUM -> {
                        gameState.difficulty = Difficulty.HARD
                        difficultyButton.setText("Hard")
                    }
                    Difficulty.HARD -> {
                        gameState.difficulty = Difficulty.EASY
                        difficultyButton.setText("Easy")
                    }
                }
                return false
            }
        })

        val backButton = TextButton("Go back", skin)
        backButton.isTransform = true
        backButton.setScale(aspectRatio*0.8f)
        backButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                if (prevScreen is DirkScreen) {
                    disabled = true
                }
                if (prevScreen is HomeScreen) {
                    game.screen = HomeScreen(game, gameState)
                } else {
                    game.screen = prevScreen
                }
                return false
            }
        })

        val table = Table()
        table.setSize(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        table.setPosition(0f, 0f)

        table.add(musicLabel).pad(aspectRatio*5f).left()
        table.add(musicVolume).pad(aspectRatio*5f).right()
        table.row()
        table.add(musicContainer).pad(aspectRatio*5f).colspan(2).left()
        table.row()
        table.add(soundLabel).pad(aspectRatio*5f).left()
        table.add(soundVolume).pad(aspectRatio*5f).right()
        table.row()
        table.add(soundContainer).pad(aspectRatio*5f).colspan(2).left()
        table.row()
        table.add(gyroscopeLabel).pad(aspectRatio*5f).left().top()
        table.add(gyroButton).pad(aspectRatio*5f).left().width(aspectRatio*30f)
        table.row()
        table.add(vibrateLabel).pad(aspectRatio*5f).left().top()
        table.add(vibrateButton).pad(aspectRatio*5f).left().width(aspectRatio*30f)
        table.row()
        table.add(difficultyLabel).pad(aspectRatio*5f).left().top()
        table.add(difficultyButton).pad(aspectRatio*5f).left().width(aspectRatio*30f)
        val backContainer = Table()
        backContainer.setSize(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        backContainer.setPosition(0f, 0f)
        backContainer.add(backButton).pad(aspectRatio*8f).expand().bottom().left()

        stage.addActor(table)
        stage.addActor(backContainer)
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