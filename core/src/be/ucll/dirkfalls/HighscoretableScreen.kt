package be.ucll.dirkfalls

import be.ucll.dirkfalls.service.HighscoreEntry
import be.ucll.dirkfalls.service.HighscoreService
import be.ucll.dirkfalls.utils.AsyncHandler
import be.ucll.dirkfalls.utils.logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Value


class HighscoretableScreen : Screen {
    var stage: Stage = Stage()
    override fun hide() {
    }

    override fun show() {
        stage = Stage()
        Gdx.input.inputProcessor = stage
        val skin = Skin(Gdx.files.internal("UI/skin.json"))

        val table = Table()
        table.setSize(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        table.setPosition(0f, 0f)
        stage.addActor(table)

        table.defaults().width(Value.percentWidth(2f))

        val highscoreService = HighscoreService()
        highscoreService.all(object : AsyncHandler<List<HighscoreEntry>> {
            override fun success(data: List<HighscoreEntry>) {
                for (entry in data) {
                    val itemLabel = Label(entry.name, skin)
                    table.add(itemLabel)
                    val scoreLabel = Label(entry.score.toString(), skin)
                    table.add(scoreLabel)
                    table.row()
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
    }

}