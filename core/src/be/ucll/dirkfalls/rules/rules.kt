package be.ucll.dirkfalls.rules

import be.ucll.dirkfalls.GameConfig
import be.ucll.dirkfalls.GameConfig.WORLD_WIDTH
import be.ucll.dirkfalls.GameState
import be.ucll.dirkfalls.entities.Comet
import be.ucll.dirkfalls.entities.HeroDirection
import be.ucll.dirkfalls.utils.between
import be.ucll.dirkfalls.utils.scale
import be.ucll.dirkfalls.utils.vector2.plus
import be.ucll.dirkfalls.utils.vector2.times
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

typealias Rule = (gameState: GameState, delta: Float) -> Unit

val updatePositionBasedOnVelocity: Rule = { gameState, delta ->
    gameState.entities
            .forEach { it.position += it.velocity * delta }
}

val heroTakesDamageWhenHit: Rule = { gameState, _ ->
    val hero = gameState.hero
    /*gameState.comets
            .filter { it.overlaps(hero) }
            .forEach {
                gameState.deleteEntity(it)
                hero.hit(it)
            }*/

    gameState.comets.forEach {
        if (it.collideWithHero(gameState.hero)) {
            gameState.deleteEntity(it)
            hero.hit(it)
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

        if (damageTimer >= GameConfig.COMET_SPAWN_TIME) {
            damageTimer = 0f // reset timer

            val hero = gameState.hero
            hero.takeDamage(5)
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
    var cometTimer = 0f

    return { gameState, delta ->
        cometTimer += delta

        if (cometTimer >= GameConfig.COMET_SPAWN_TIME) {
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
    var cometTimer = 0f

    return { gameState, delta ->
        cometTimer += delta

        if (cometTimer >= GameConfig.COMET_SPAWN_TIME) {
            cometTimer = 0f // reset timer

            val cometRadius = 0.2f
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
    var cometTimer = 0f

    var vector_y = MathUtils.random(3f, 6f)
    var vector2velocity = Vector2(0f, vector_y * (-1))
    return { gameState, delta ->
        cometTimer += delta
        var vector_y = MathUtils.random(3f, 6f)
        var vector2velocity = Vector2(0f, vector_y * (-1))
        if (cometTimer >= GameConfig.COMET_SPAWN_TIME) {
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

fun createCometsWithVelocityAndSize(): Rule {
    var cometTimer = 0f

    return { gameState, delta ->
        cometTimer += delta
        var vector_y = MathUtils.random(3f, 6f)
        var vector2velocity = Vector2(0f, vector_y * (-1))
        if (cometTimer >= GameConfig.COMET_SPAWN_TIME) {
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

fun spawnCometForIntroScreen(): Rule {
    var cometTimer = 0f


    return { gameState, delta ->
        cometTimer += delta
        val vector_y = MathUtils.random(3f, 7f)
        val vector2velocity = Vector2(0f, vector_y * (-1))
        if (cometTimer >= GameConfig.COMET_SPAWN_TIME / 3) {
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

val changeColor: Rule = { gameState, delta ->
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
            pressed.x < hero.radius ->
                hero.direction = when {
                    pressed.x <= hero.position.x + hero.shape.radius -> HeroDirection.RIGHT
                    pressed.x > hero.position.x + hero.shape.radius -> HeroDirection.LEFT
                    else -> HeroDirection.STILL
                }
            pressed.x > WORLD_WIDTH - hero.radius ->
                hero.direction = when {
                    pressed.x < hero.position.x -> HeroDirection.RIGHT
                    pressed.x >= hero.position.x -> HeroDirection.LEFT
                    else -> HeroDirection.STILL
                }
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

