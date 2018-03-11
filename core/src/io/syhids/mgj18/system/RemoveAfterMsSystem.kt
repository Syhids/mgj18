package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import io.syhids.mgj18.Particle.RemoveAfterTimeComponent

class RemoveAfterMsSystem : IteratingSystem(Family.all(
    RemoveAfterTimeComponent::class.java
).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val removeComponent = entity.getComponent(RemoveAfterTimeComponent::class.java)

        if (removeComponent.lifetime <= 0f) return

        removeComponent.lifetime -= deltaTime

        if (removeComponent.lifetime <= 0f) {
            engine.removeEntity(entity)
        }
    }
}