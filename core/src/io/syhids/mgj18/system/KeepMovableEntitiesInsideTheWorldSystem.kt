package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import io.syhids.mgj18.*
import ktx.math.times

class KeepMovableEntitiesInsideTheWorldSystem(
    val rect: Rectangle
) : DebugIteratingSystem(Family.all(
    PositionComponent::class.java,
    VelocityComponent::class.java
).get()) {
    private val positionCache = cacheOfComponent(PositionComponent::class)
    private val velocityCache = cacheOfComponent(VelocityComponent::class)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (entity.hasComponent<ShootComponent>()) return
        if (entity is BackgroundClouds) return

        val position = positionCache.get(entity)
        val velocity = velocityCache.get(entity)

        if (!rect.contains(position.x, position.y)) {
            val centerOfTheWorld = Vector2(0f, 0f)
            val dir = (centerOfTheWorld - position).nor()

            position += dir * 70f * deltaTime
            velocity *= 0.4f
        }
    }
}