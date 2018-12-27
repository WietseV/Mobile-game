package be.ucll.dirkfalls.utils

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle

inline fun ShapeRenderer.use(action: () -> Unit) {
    begin(ShapeRenderer.ShapeType.Line)
    action()
    end()
}

@JvmOverloads
fun ShapeRenderer.circle(c: Circle, segments: Int = 30) {
    circle(c.x, c.y, c.radius, segments)
}

@JvmOverloads
fun ShapeRenderer.rect(rec: Rectangle) {
    rect(rec.x, rec.y, rec.width, rec.height)
}

@JvmOverloads
fun ShapeRenderer.batchRender(c: Circle, segments: Int = 30) {
    circle(c.x + c.radius, c.y + c.radius, c.radius, segments)
}

@JvmOverloads
fun ShapeRenderer.heroRender(c: Circle, segments: Int = 30) {
    circle(c.x, c.y + c.radius, c.radius, segments)
}

@JvmOverloads
fun ShapeRenderer.rectForBatch(rect: Rectangle) {
    rect(rect.x + rect.width / 2, rect.y + rect.height / 2, rect.width, rect.height)
}

inline fun SpriteBatch.use(action: (SpriteBatch) -> Unit) {
    this.begin()
    action(this)
    this.end()
}