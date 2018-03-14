package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import io.syhids.mgj18.Particle
import io.syhids.mgj18.Particle.ParticleDrawingComponent
import io.syhids.mgj18.Particle.RemoveAfterTimeComponent

class RemoveAfterMsSystem : DebugIteratingSystem(Family.all(
    RemoveAfterTimeComponent::class.java
).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val removeComponent = entity.getComponent(RemoveAfterTimeComponent::class.java)

        if (removeComponent.lifetime <= 0f) return

        removeComponent.lifetime -= deltaTime

        if (removeComponent.lifetime <= 0f) {
            engine.removeEntity(entity)

            entity.getComponent<Particle.ParticleDrawingComponent>(ParticleDrawingComponent::class.java)
                .effect.free()
        }
    }
}