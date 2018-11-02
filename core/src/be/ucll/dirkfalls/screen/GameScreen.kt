package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.GameConfig.GameConfig
import be.ucll.dirkfalls.Vector2
import be.ucll.dirkfalls.common.SampleBase
import be.ucll.dirkfalls.entities.Comet
import be.ucll.dirkfalls.entities.Entity
import be.ucll.dirkfalls.entities.Hero
import be.ucll.dirkfalls.utils.drawGrid
import be.ucll.dirkfalls.utils.use
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

class GameScreen : Screen {

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var renderer: ShapeRenderer
    private lateinit var hero: Hero
    private val entities = mutableListOf<Comet>()

    private var cometTimer = 0f

    override fun hide() {
    }

    override fun show() {
        camera = OrthographicCamera()
        //camera.zoom = 2f //zet deze uit op het origineel bord te zien
        viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()

        //create player

        var positionXHero = GameConfig.WORLD_WIDTH / 2f
        var startVector2: Vector2 = Vector2(positionXHero, 1f)
        hero = Hero(startVector2)

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        hero.moveX()
        updateComet()
        createComet(delta)

        renderer.projectionMatrix = camera.combined
        renderer.use {
            hero.drawDebug(renderer)
            entities.forEach{ it.drawDebug(renderer)}
        }

        viewport.drawGrid(renderer)
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

    private fun updateComet() {
        entities.forEach {
            renderer.use { it.fallDown() }
        }
    }

    private fun createComet(delta: Float) {
        cometTimer += delta

        if (cometTimer >= GameConfig.COMET_SPAWN_TIME) {
            cometTimer = 0f // reset timer

            val cometX = MathUtils.random(0f, GameConfig.WORLD_WIDTH)
            val vector2 = Vector2(cometX, GameConfig.WORLD_HEIGHT )

            val comet = Comet(vector2)
            entities.add(comet)

        }
    }

    private fun  blockPlayerFromLeaving(){
        
    }

}