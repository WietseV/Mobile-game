package be.ucll.dirkfalls.entities

import be.ucll.dirkfalls.utils.vector2.plus
import be.ucll.dirkfalls.utils.vector2.times

typealias Rule = (entityManager: EntityManager, delta: Float) -> Unit

val updatePositionBasedOnVelocity: Rule = { entityManager, delta ->
    entityManager.entities
        .forEach { it.position += it.velocity * delta }
}

val heroTakesDamageWhenHit: Rule = { entityManager, _ ->
    val hero = entityManager.hero
    entityManager.comets
        .filter { it.overlaps(hero) }
        .forEach {
            entityManager.deleteEntity(it)
            hero.hit()
        }
}

val removeCometWhenOutOfBound: Rule = { entityManager, _ ->
    entityManager.comets
        .filter { it.position.y + 1f < 2f }
        .forEach { entityManager.deleteEntity(it) }
}

val heroCannotMoveOutOfBounds: Rule = { entityManager, delta ->
    val hero = entityManager.hero
    if (hero.outOfBounds(delta)) {
        hero.direction = HeroDirection.STILL
    }
}