package dirk.ucll.be.dirkfalls

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import dirk.ucll.be.dirkfalls.renderer.SysoutRenderer
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.hello_text)
        textView.text = "Hello dirk"

        val renderer = SysoutRenderer()
        val board = Board(1f, 1f, renderer)

        val fps = 30L
        val msInSecond = 1000L
        val timeBetweenFrames = msInSecond / fps
        val gameLoop = Timer("Game loop", true)
        var previousTime = System.currentTimeMillis()
        gameLoop.scheduleAtFixedRate(0, timeBetweenFrames) {
            val currentTime = System.currentTimeMillis()
            val difference = currentTime - previousTime
            previousTime = currentTime
            board.updateEntities(difference / 1000.0f)
        }
     }
}
