package be.ucll.dirkfalls.samples

import be.ucll.dirkfalls.common.SampleBase
import be.ucll.dirkfalls.utils.logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport


/*dit is een voorbeeld om hoe je dingen moet tekenen of representeren in uw gbx,
Dit is gewoon mijn code om dingen uit te proberen. hopelijk kunnen jullie hier ook iets van opsteken */
class ShapeRenderSample : SampleBase() {

    companion object {
        @JvmStatic
        private val log = logger<ShapeRenderSample>()

        private const val WORLD_WIDTH = 40f
        private const val WORLD_HEIGHT = 20f

    }

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: FitViewport
    private lateinit var renderer: ShapeRenderer


    private var drawGrid = false
    private var drawCircles = false
    private var drawRectangles = false
    private var drawPoints = true

    override fun create() {
        camera = OrthographicCamera()
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()

        Gdx.input.inputProcessor = this

    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.projectionMatrix = camera.combined


        if (drawGrid) {
            drawGrid()
        }

        if (drawCircles) {
            drawCircle()
        }

        if (drawRectangles) {
            drawRectangle()
        }
        if (drawPoints) {
            drawPoint()
        }
    }

    private fun drawPoint() {
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        renderer.color = Color.CORAL

        renderer.point(3f, -8f, 0f)
        renderer.point(-8f, 3f, 0f)
        renderer.point(-7f, -2f, 0f)

        renderer.end()


    }

    private fun drawRectangle() {
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        renderer.color = Color.YELLOW

        renderer.rect(3f, 2f, 3f, 4f)


        renderer.end()
    }

    private fun drawCircle() {
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        renderer.color = Color.GREEN

        renderer.circle(2f, 2f, 2f, 30)
        renderer.circle(5f, -5f, 5f)


        renderer.end()
    }

    private fun drawGrid() {
        //druk g en de grid zal zich tekenen
        //grid is vult niet heel het scherm omdat ik een fitviewport gebruik
        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.color = Color.WHITE

        val worldWidth = WORLD_WIDTH.toInt()
        val worldHeight = WORLD_HEIGHT.toInt()

        //horizontal Lines
        for (y in -worldHeight until worldHeight) {
            renderer.line(-worldWidth.toFloat(), y.toFloat(), worldWidth.toFloat(), y.toFloat())
        }

        for (x in -worldWidth until worldHeight) {
            renderer.line(x.toFloat(), -worldHeight.toFloat(), x.toFloat(), worldHeight.toFloat())
        }

        //twee assen tekenen

        renderer.color = Color.RED
        renderer.line(-worldWidth.toFloat(), 0f, worldWidth.toFloat(), 0f)
        renderer.line(0f, -worldHeight.toFloat(), 0f, worldHeight.toFloat())
        renderer.end()
    }

    override fun keyDown(keycode: Int): Boolean {
        //zeer handige methode om meerdere dingen te checken. Werkt ongeveer als if elseif, minder
        //schrijf werk, mooiere code

        when (keycode) {
            Input.Keys.G -> drawGrid = !drawGrid //drawgrid wordt het tegenovergesteld
            Input.Keys.C -> drawCircles = !drawCircles
            Input.Keys.R -> drawRectangles = !drawRectangles
            Input.Keys.P -> drawPoints = !drawPoints

        }
        return true
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun dispose() {
        renderer.dispose()

    }

}




