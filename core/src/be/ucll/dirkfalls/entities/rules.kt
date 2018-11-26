package be.ucll.dirkfalls.entities

typealias Rule = (entityManager: EntityManager, delta: Float) -> Unit

val heroTakesDamageWhenHit: Rule = { entityManager, _ ->
    val hero = entityManager.hero
    entityManager.comets
        .filter { hero.overlaps(it) }
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