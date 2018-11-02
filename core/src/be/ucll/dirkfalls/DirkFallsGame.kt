package be.ucll.dirkfalls

import be.ucll.dirkfalls.entities.Entity
import be.ucll.dirkfalls.entities.Hero
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import be.ucll.dirkfalls.utils.logger
import com.badlogic.gdx.Application

class DirkFallsGame : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var img: Texture
    private val entities = mutableListOf<Entity>()

    companion object {
        @JvmStatic
        private val log = logger<ApplicationAdapter>()
    }
    override fun create() {

        Gdx.app.logLevel = Application.LOG_DEBUG

        log.debug("yo") // dit komt in console, you're welcome
        batch = SpriteBatch()
        img = Texture("badlogic.jpg")
        val hero = Hero(
                position = Vector2(50f, 50f),
                size = Vector2(10f, 10f),
                velocity = Vector2.Origin
        )

        entities.add(hero)
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        entities.forEach {
            batch.draw(img, it.position.x, it.position.y)
        }
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
    }
}