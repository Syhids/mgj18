package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Circle
import io.syhids.mgj18.*

class BulletColissionSystem : IteratingSystem(Family.all(
        ShootComponent::class.java
).get()) {
    private val circleColliderCache = component(CircleColliderComponent::class)
    private val shootComponent = component(ShootComponent::class)


    override fun processEntity(entity: Entity, deltaTime: Float) {

        val bullet = entity
        val enemies = engine.entities.filter { it is Enemy }
                .map { it to Circle(it.position.x, it.position.y, circleColliderCache.get(it).radius) }


        val enemyCollided = enemies.firstOrNull { (_, circle) -> circle.contains(bullet.position.x, bullet.position.y) }

        if (enemyCollided != null) {
            engine.removeEntity(entity)
            engine.removeEntity(enemyCollided.first)
        }
    }
}