package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Circle
import io.syhids.mgj18.*

class BulletCollisionSystem : IteratingSystem(Family.all(
    ShootComponent::class.java
).get()) {
    private val circleColliderCache = cacheOfComponent(CircleColliderComponent::class)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val bullet = entity
        val enemies = engine.entities.filter { it.hasComponent<EnemyComponent>() }
            .map { it to Circle(it.position.x, it.position.y, circleColliderCache.get(it).radius) }

        val (enemy, circle) = enemies.firstOrNull { (_, circle) ->
            circle.contains(bullet.position.x, bullet.position.y)
        }
            ?: return

        if (enemy != null) {
            engine.removeEntity(entity)

            if (enemy.hasComponent<DeadableComponent>()) {
                val deadable = enemy.getComponent(DeadableComponent::class.java)
                deadable.hit = true
                deadable.hitSource = DeadableComponent.HitSource.Bullet
            }
        }
    }
}