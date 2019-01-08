package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.GameState
import be.ucll.dirkfalls.entities.Comet
import be.ucll.dirkfalls.entities.HeroDirection
import be.ucll.dirkfalls.entities.Power
import be.ucll.dirkfalls.utils.between
import be.ucll.dirkfalls.utils.glebscale
import be.ucll.dirkfalls.utils.scale
import be.ucll.dirkfalls.utils.vector2.plus
import be.ucll.dirkfalls.utils.vector2.times
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.MathUtils.sin
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

var cometTimer = 0f
var powerFallingTimer = 0f
var standardPowerTimer = 5f
var powerTimer = standardPowerTimer
var sinCount = 0

typealias Rule = (gameState: GameState, delta: Float) -> Unit


val updatePositionBasedOnVelocity: Rule = { gameState, delta ->
    gameState.entities
            .forEach { it.position += it.velocity * delta }
}

val heroTakesDamageWhenHit: Rule = { gameState, _ ->
    val hero = gameState.hero


    gameState.comets.forEach {
        if (it.collideWithHero(gameState.hero)) {
            gameState.deleteEntity(it)
            if (!hero.superDirkActive) {
                hero.hit(it)
                gameState.hitSound.play(gameState.sound/100f)
                if (gameState.useVibration) Gdx.input.vibrate(200)

            }
        }
    }


}

val heroHealsWhenHit: Rule = { gameState, _ ->
    val hero = gameState.hero
    gameState.comets.forEach {
        if (it.collideWithHero(gameState.hero)) {
            gameState.deleteEntity(it)
            hero.hitHeal(it)
            gameState.score++
        }
    }

}

fun heroTakesDamageOverTime(): Rule {
    var damageTimer = 0f


    return { gameState, delta ->
        damageTimer += delta
        var difficulty = gameState.properDiffeculty()


        if (damageTimer >= difficulty) {
            damageTimer = 0f // reset timer

            val hero = gameState.hero
            hero.takeDamage(5)
            if (gameState.useVibration) {
                Gdx.input.vibrate(100)
            }
        }
    }

}

val scoreWhenCometOutOfBound: Rule = { gameState, _ ->
    gameState.comets
            .filter { it.position.y < 0f }
            .forEach {
                gameState.deleteEntity(it)
                gameState.score++
            }
}

val deleteComentsOutOfBounds: Rule ={gameState, _ ->
    gameState.comets
            .filter { it.position.y < 0f - 2* it.shape.radius}
            .forEach {
                gameState.deleteEntity(it)
            }
    gameState.powers
            .filter { it.position.y < 0f - 2* it.shape.radius}
            .forEach {
                gameState.deleteEntity(it)
            }
}

val noScoreWhenCometOutOfBound: Rule = { gameState, _ ->
    gameState.comets
            .filter { it.position.y < 0f }
            .forEach {
                gameState.deleteEntity(it)
            }
}


val heroCannotMoveOutOfBounds: Rule = { gameState, delta ->
    val hero = gameState.hero
    if (hero.outOfBounds(delta)) {
        if (hero.gyro) {
            hero.velocity = Vector2.Zero
        } else {
            hero.direction = HeroDirection.STILL
        }
    }
}

fun createCometSpawnerAndSize(): Rule {

    return { gameState, delta ->
        cometTimer += delta
        var difficulty = gameState.properDiffeculty()

        if (cometTimer >= difficulty) {
            cometTimer = 0f // reset timer

            val cometRadius = MathUtils.random(0.1f, 0.3f)
            val cometX = MathUtils.random(0f, GameConfig.WORLD_WIDTH - (2 * cometRadius))
            //de formule hier boven dient er voor zodat de batch de kometen fatsoenlijk tekent

            val vector2 = Vector2(cometX, GameConfig.WORLD_HEIGHT + cometRadius)
            val comet = Comet(vector2, cometRadius)
            if (gameState.comets.none { it.collide(comet.shape) }) {
                gameState.entities.add(comet)
            }
        }
    }
}

fun createCometSpawner(): Rule {

    return { gameState, delta ->
        cometTimer += delta
        var difficulty = gameState.properDiffeculty()
        println(difficulty)
        if (cometTimer >= difficulty) {
            cometTimer = 0f // reset timer

            val cometRadius = 0.22f //Verhoogt van 0.20f naar 0.22f zodat hero sterft na 3 hits en niet met een sliver health overleeft
            val cometX = MathUtils.random(0f, GameConfig.WORLD_WIDTH - (2 * cometRadius))
            //de formule hier boven dient er voor zodat de batch de kometen fatsoenlijk tekent

            val vector2 = Vector2(cometX, GameConfig.WORLD_HEIGHT + cometRadius)
            val comet = Comet(vector2, cometRadius)
            if (gameState.comets.none { it.collide(comet.shape) }) {
                gameState.entities.add(comet)
            }
        }
    }
}

fun createCometsWithVelocity(): Rule {


    return { gameState, delta ->
        cometTimer += delta
        var difficulty = gameState.properDiffeculty()
        println(difficulty)
        val vectory = MathUtils.random(3f, 6f)
        val vector2velocity = Vector2(0f, vectory * (-1))
        if (cometTimer >= difficulty) {
            cometTimer = 0f // reset timer

            val cometRadius = 0.2f
            val cometX = MathUtils.random(0f, GameConfig.WORLD_WIDTH - (2 * cometRadius))
            //de formule hier boven dient er voor zodat de batch de kometen fatsoenlijk tekent

            val vector2 = Vector2(cometX, GameConfig.WORLD_HEIGHT + cometRadius)
            val comet = Comet(vector2, cometRadius, vector2velocity)
            if (gameState.comets.none { it.collide(comet.shape) }) {
                gameState.entities.add(comet)
            }
        }
    }
}

fun rulePowers(): Rule {


    return { gameState, delta ->
        powerFallingTimer += delta


        if (powerFallingTimer >= GameConfig.POWE_SPAWN_TIME) {
            powerFallingTimer = 0f // reset timer

            val cometRadius = 0.22f //Verhoogt van 0.20f naar 0.22f zodat hero sterft na 3 hits en niet met een sliver health overleeft
            val cometX = MathUtils.random(0f, GameConfig.WORLD_WIDTH - (2 * cometRadius))
            //de formule hier boven dient er voor zodat de batch de kometen fatsoenlijk tekent

            val vector2 = Vector2(cometX, GameConfig.WORLD_HEIGHT + cometRadius)
            val power = Power(vector2, cometRadius)
            if (gameState.powers.none { it.collide(power.shape) }) {
                gameState.entities.add(power)
            }
        }

        // verdere power regels zoals dat dirk zijn skin veranderd en geen damage meer neemt.
        if (gameState.hero.superDirkActive) {
            if (powerTimer > 0) {
                powerTimer -= delta
            } else {
                gameState.hero.superDirkActive = false
                powerTimer = standardPowerTimer
            }
        }
        gameState.powers.forEach {
            if (it.collideWithHero(gameState.hero)) {
                gameState.deleteEntity(it)
                gameState.hero.superDirkActive = true

                gameState.powerSound.play(gameState.sound/100f)
                powerTimer = standardPowerTimer
                if (gameState.useVibration) {
                    Gdx.input.vibrate(500)
                }

            }
        }
    }
}


@Deprecated("Not yet used")
fun createCometsWithVelocityAndSize(): Rule {

    return { gameState, delta ->
        cometTimer += delta
        val vectory = MathUtils.random(3f, 6f)
        val vector2velocity = Vector2(0f, vectory * (-1))
        var difficulty = gameState.properDiffeculty()

        if (cometTimer >= difficulty) {
            cometTimer = 0f // reset timer

            val cometRadius = MathUtils.random(0.1f, 0.3f)
            val cometX = MathUtils.random(0f, GameConfig.WORLD_WIDTH - (2 * cometRadius))
            //de formule hier boven dient er voor zodat de batch de kometen fatsoenlijk tekent

            val vector2 = Vector2(cometX, GameConfig.WORLD_HEIGHT + cometRadius)
            val comet = Comet(vector2, cometRadius, vector2velocity)
            if (gameState.comets.none { it.collide(comet.shape) }) {
                gameState.entities.add(comet)
                println(gameState.entities.size)
            }
        }
    }
}

fun fallingLikeSin(): Rule{
    return { gameState, delta ->
        cometTimer += delta
        sinCount += MathUtils.random(1,3)
        var difficulty = gameState.properDiffeculty()
        if (cometTimer >= difficulty) {
            cometTimer = 0f // reset timer

            val cometRadius = MathUtils.random(0.1f, 0.3f)
            //de formule hier boven dient er voor zodat de batch de kometen fatsoenlijk tekent
            var floatSin =  cos(sinCount.toFloat()%100f)
            println(floatSin)
            val vectoriets = glebscale((floatSin) + 1f, 0f, 2f,0f,GameConfig.WORLD_WIDTH - (2 * cometRadius) )
            // ik weet dat de functie hier boven een beetje vaag er uit ziet, maar dit komt omdat
            // x soms negatief is, en dan kreeg ik het niet geregeld.  x Gleb
            val vector2 = Vector2(vectoriets, GameConfig.WORLD_HEIGHT + cometRadius)
            val comet = Comet(vector2, cometRadius)
            if (gameState.comets.none { it.collide(comet.shape) }) {
                gameState.entities.add(comet)
            }
        }
    }
}
fun spawnCometForIntroScreen(): Rule {

    return { gameState, delta ->
        cometTimer += delta
        val vectory = MathUtils.random(3f, 7f)
        val vector2velocity = Vector2(0f, vectory * (-1))
        if (cometTimer >= GameConfig.COMET_SPAWN_TIME_MEDIUM / 3) {
            cometTimer = 0f // reset timer

            val cometRadius = MathUtils.random(0.4f, 0.7f)
            val cometX = MathUtils.random(0f, GameConfig.WORLD_WIDTH - (2 * cometRadius))
            //de formule hier boven dient er voor zodat de batch de kometen fatsoenlijk tekent

            val vector2 = Vector2(cometX, GameConfig.WORLD_HEIGHT + cometRadius)
            val comet = Comet(vector2, cometRadius, vector2velocity)
            if (gameState.comets.none { it.collide(comet.shape) }) {
                gameState.entities.add(comet)
            }
        }
    }
}

val changeColor: Rule = { gameState, _ ->
    gameState.comets.forEach {
        it.color = Vector3(
                scale(it.position.x, 0f, GameConfig.WORLD_WIDTH),
                scale(it.position.y, 0f, GameConfig.WORLD_HEIGHT),
                0.5f
        )
    }
}

val gyroscope: Rule = { gameState, _ ->
    gameState.hero.gyro = true
    val gyroY = Gdx.input.gyroscopeY
    gameState.hero.velocity = Vector2(scale(gyroY, 0f, 1f, 0f, 5f), 0f)
}

val touchScreen: Rule = { gameState, _ ->
    val hero = gameState.hero
    hero.gyro = false
    val pressed = gameState.pressedPosition
    if (pressed != null) {
        hero.direction = when {
            between(
                    pressed.x,
                    hero.position.x,
                    hero.position.x + hero.shape.radius
            ) -> HeroDirection.STILL
            pressed.x < hero.position.x -> HeroDirection.LEFT
            pressed.x > hero.position.x + hero.shape.radius -> HeroDirection.RIGHT
            else -> HeroDirection.STILL
        }
    } else {
        hero.direction = HeroDirection.STILL
    }
}

val touchScreenInverted: Rule = { gameState, _ ->
    val hero = gameState.hero
    hero.gyro = false
    val pressed = gameState.pressedPosition
    if (pressed != null) {
        when {
            pressed.x < (hero.position.x + hero.shape.radius) ->
                hero.direction = HeroDirection.RIGHT

            pressed.x >= (hero.position.x ) ->
                hero.direction =  HeroDirection.LEFT

            else ->
                hero.direction = when {
                    between(
                            pressed.x,
                            hero.position.x,
                            hero.position.x + hero.shape.radius
                    ) -> HeroDirection.STILL
                    pressed.x < hero.position.x -> HeroDirection.RIGHT
                    pressed.x > hero.position.x + hero.shape.radius -> HeroDirection.LEFT
                    else -> HeroDirection.STILL
                }
        }
    } else {
        hero.direction = HeroDirection.STILL
    }
}

