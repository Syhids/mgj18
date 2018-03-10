package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import io.syhids.mgj18.*

class AccelerationSystem : IteratingSystem(Family.all(
    PositionComponent::class.java,
    VelocityComponent::class.java
).get()) {
    private val position = component(PositionComponent::class)
    private val velocity = component(VelocityComponent::class)
    private val friction = component(FrictionComponent::class)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = position.get(entity)
        val velocity = velocity.get(entity)

        position.x += velocity.x * deltaTime
        position.y += velocity.y * deltaTime

        entity.friction?.let { friction ->
            velocity.x *= 1f - friction.value
            velocity.y *= 1f - friction.value
        }
    }
}