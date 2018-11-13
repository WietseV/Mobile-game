package be.ucll.dirkfalls

import android.content.Context
import android.view.MotionEvent
import android.view.View
import be.ucll.dirkfalls.utils.TouchHandler

class GameView(context: Context) : View(context) {

    override fun onTouchEvent(event: MotionEvent): Boolean {
        TouchHandler.touch = floatArrayOf(event.x, event.y)
        return true
    }
}
