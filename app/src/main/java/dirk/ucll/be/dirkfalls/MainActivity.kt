package dirk.ucll.be.dirkfalls

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.hello_text)
        textView.text = "Hello dirk"

        val board = Board(1f, 1f)
        val fps = 30L
        val msInSecond = 1000L
        val timeBetweenFrames = msInSecond / fps
        val gameLoop = Timer("Game loop", true)
        gameLoop.scheduleAtFixedRate(0, timeBetweenFrames) {
            board.updateEntities()
        }
     }
}
