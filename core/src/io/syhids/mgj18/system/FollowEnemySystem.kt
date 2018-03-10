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

    private val positionCache = component(PositionComponent::class)
    private val velocityCache = component(VelocityComponent::class)
    private val enemyComponentCache = component(EnemyComponent::class)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val hero = engine.entities.first { it is Hero }
        val enemy = entity
        val enemyPosition = positionCache.get(enemy)

        val direction = (hero.position - enemy.position).nor()

        val totalMovementVector = direction * deltaTime * enemyComponentCache.get(enemy).velocityMultiplier

        val futureDistanceBetweenBoth: Vector2 = enemyPosition + totalMovementVector - hero.position

        if (futureDistanceBetweenBoth.len2() > MIN_DIST * MIN_DIST) {
            val enemyVelocity = velocityCache.get(enemy)
            enemyVelocity += totalMovementVector
        }
    }
}

private operator fun Vector2.minus(position: PositionComponent) = this - vec2(position.x, position.y)