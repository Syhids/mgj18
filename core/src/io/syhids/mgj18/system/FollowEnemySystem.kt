package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import io.syhids.mgj18.*
import ktx.math.minus
import ktx.math.times
import ktx.math.vec2

class FollowEnemySystem : IteratingSystem(Family.all(
    EnemyComponent::class.java
).get()) {
    private val MIN_DIST = 80

    private val position = component(PositionComponent::class)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val hero = engine.entities.first { it is Hero }
        val enemy = entity
        val enemyPosition = position.get(enemy)

        val direction = (hero.position - enemy.position).nor()

        val totalMovementVector = direction * 2

        val futureDistanceBetweenBoth: Vector2 = enemyPosition + totalMovementVector - hero.position

        if (futureDistanceBetweenBoth.len2() > MIN_DIST * MIN_DIST) {
            enemyPosition += totalMovementVector
        }
    }
}

private operator fun Vector2.minus(position: PositionComponent) = this - vec2(position.x, position.y)