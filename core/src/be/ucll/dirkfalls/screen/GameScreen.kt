package be.ucll.dirkfalls.screen

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.GameConfig.WORLD_HEIGHT
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.entities.Comet
import be.ucll.dirkfalls.entities.HealthBar
import be.ucll.dirkfalls.entities.Hero
import be.ucll.dirkfalls.entities.HeroDirection
import be.ucll.dirkfalls.utils.TouchHandler
import be.ucll.dirkfalls.utils.isKeyPressed
import be.ucll.dirkfalls.utils.use
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

class GameScreen : Screen {

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var renderer: ShapeRenderer
    private lateinit var batch: SpriteBatch
    private lateinit var font: BitmapFont
    private lateinit var hero: Hero
    private lateinit var health: HealthBar
    private var paused: Boolean = false
    private val entities = mutableListOf<Comet>()
    private var cometTimer = 0f
    private var score = 0

    override fun hide() {
    }

    override fun show() {
        camera = OrthographicCamera()
        //camera.zoom = 2f //zet deze uit op het origineel bord te zien
        viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()
        batch = SpriteBatch(20)
        font = BitmapFont()
        font.color = Color.WHITE

        //create player
        hero = Hero(Vector2(WORLD_WIDTH / 2f, 1f))
        health = HealthBar(Vector2(WORLD_WIDTH-2f, WORLD_HEIGHT-0.4f))

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if(!paused) {
            update(delta)
        }

        renderer.projectionMatrix = camera.combined
        renderer.use {
            renderer.setAutoShapeType(true)
            renderer.set(ShapeRenderer.ShapeType.Filled)
            renderer.setColor(0f,0f,255f,100f)
            renderer.rect(0f,1f, WORLD_WIDTH, WORLD_HEIGHT)
            renderer.setColor(0f,255f,0f,100f)
            renderer.rect(0f,0f, WORLD_WIDTH, 1f)
            renderer.setColor(255f,255f,255f,100f)
            hero.drawDebug(renderer)
            entities.forEach { it.drawDebug(renderer) }
            renderer.setColor(0f,255f,0f,100f)
            health.drawDebugGreen(renderer)
            renderer.setColor(255f,0f,0f,100f)
            health.drawDebugRed(renderer)
            renderer.setColor(255f,255f,255f,100f)
        }

        //Score not yet shown TODO
        batch.begin()
        val char: CharSequence = score.toString()
        font.draw(batch, char, WORLD_WIDTH/2, WORLD_HEIGHT/2)
        batch.end()

        //viewport.drawGrid(renderer)
    }

    private fun update(delta: Float) {
        hero.direction = when {
            Input.Keys.RIGHT.isKeyPressed() -> HeroDirection.RIGHT
            Input.Keys.LEFT.isKeyPressed() -> HeroDirection.LEFT
            TouchHandler.touch[0] < WORLD_WIDTH/2 -> HeroDirection.LEFT
            TouchHandler.touch[0] > WORLD_WIDTH/2 -> HeroDirection.RIGHT
            else -> HeroDirection.STILL
        }
        updateHero(delta)
        updateComet(delta)
        updateHealth()
        createComet(delta)
    }

    private fun updateHealth() {

        renderer.use {health.setRed(hero.health)}
        if (hero.health == 0) {
            pause()
        }

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
        batch.dispose()
        font.dispose()
    }

    private fun updateHero(delta: Float) {
        if (hero.outOfBounds(delta)) {
            hero.direction = HeroDirection.STILL
        }
        else {
            hero.update(delta)
        }
    }

    private fun updateComet(delta: Float) {
        val entRemove = mutableListOf<Comet>()
        entities.forEach {
            if (it.overlaps(hero)) {
                entRemove.add(it)
                hero.hit()
            }
            if (it.position.y+1f < 2f) { //Comet destroyed after hitting ground (set to 0f for destruction out of bounds)
                entRemove.add(it)
                score++
                println(score)
            }
            renderer.use { it.update(delta) }
        }
        entities.removeAll(entRemove)
    }

    private fun createComet(delta: Float) {
        cometTimer += delta

        if (cometTimer >= GameConfig.COMET_SPAWN_TIME) {
            cometTimer = 0f // reset timer

            val cometRadius = MathUtils.random(0.1f, 0.3f)
            val cometX = MathUtils.random(0f+cometRadius, GameConfig.WORLD_WIDTH - cometRadius)
            val vector2 = Vector2(cometX, GameConfig.WORLD_HEIGHT + cometRadius)
            val comet = Comet(vector2,cometRadius)
            if (checkCometSpawn(comet)) {
                entities.add(comet)
            }
        }
    }

    private fun checkCometSpawn(comet: Comet): Boolean {
        entities.forEach {
            if (it.overlaps(comet)) {
                return false
            }
        }
        return true
    }

    private fun blockPlayerFromLeaving(){

    }

}
