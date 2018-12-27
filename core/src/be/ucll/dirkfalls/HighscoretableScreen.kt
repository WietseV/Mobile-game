package be.ucll.dirkfalls

import be.ucll.dirkfalls.service.HighscoreEntry
import be.ucll.dirkfalls.service.HighscoreService
import be.ucll.dirkfalls.utils.AsyncHandler
import be.ucll.dirkfalls.utils.logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*


class HighscoretableScreen :
            Screen {
        var stage: Stage = Stage()
    override fun hide() {
        }

        fun getTop10(): ArrayList<HighscoreEntry> {
            var counter = 0
            val list = ArrayList<HighscoreEntry>(10)
            val highscoreService = HighscoreService()
            highscoreService.all(object : AsyncHandler<List<HighscoreEntry>> {
                override fun success(data: List<HighscoreEntry>) {
                    val logger = logger<DirkFallsGame>()
                    for(entry in data) {
                        while (counter < 10) {
                            logger.info(entry.name + " -- " + entry.score)
                            list.add(entry)
                            logger<DirkFallsGame>().info(list.size.toString())
                            counter += 1
                        }
                    }

                }
                override fun error(t: Throwable) {
                    logger<DirkFallsGame>().error(t.toString(), t)
                }
            })
            return list
        }

        override fun show() {
            val list =getTop10()

            stage = Stage()
            Gdx.input.inputProcessor = stage
            val skin = Skin(Gdx.files.internal("UI/skin.json"))


            val table = Table()
            table.setSize(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
            table.setPosition(0f, 0f)
            stage.addActor(table)

            table.debug()


            table.defaults().width(Value.percentWidth(2f))
            val nameLabel = Label("Name", skin)
            table.add(nameLabel)
            val scoreLabel = Label("Score", skin)
            table.add(scoreLabel)
            table.row()
            logger<DirkFallsGame>().info(list.size.toString())
            for (entry in list){
                val itemLabel = Label(entry.name, skin)
                table.add(itemLabel)
                val scoreLabel = Label("10", skin)
                table.add(scoreLabel)
                table.row()
            }
        }

        override fun render(delta: Float) {
            Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1f)
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
            //stage.act(Math.min(Gdx.graphics.deltaTime, 1 / 30f))
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