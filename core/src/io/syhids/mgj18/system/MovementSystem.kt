package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import io.syhids.mgj18.MovableComponent
import io.syhids.mgj18.VelocityComponent
import io.syhids.mgj18.movement
import io.syhids.mgj18.velocity

class MovementSystem : IteratingSystem(Family.all(
    VelocityComponent::class.java,
    MovableComponent::class.java
).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val power = entity.velocity.POWER
        val maxVelocity = entity.velocity.MAX_VELOCITY
        val movement = entity.movement

        if (movement.shouldMoveLeft) {
            entity.velocity.x -= deltaTime * power
        } else if (movement.shouldMoveRight) {
            entity.velocity.x += deltaTime * power
        } else {
            entity.velocity.x *= 0.9f
        }

        entity.velocity.x = Math.min(maxVelocity, entity.velocity.x)
        entity.velocity.x = Math.max(-maxVelocity, entity.velocity.x)
    }
}