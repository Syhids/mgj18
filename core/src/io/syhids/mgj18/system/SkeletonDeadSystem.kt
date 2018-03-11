package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import io.syhids.mgj18.DeadableComponent
import io.syhids.mgj18.Skeleton.SkeletonComponent

class SkeletonDeadSystem : IteratingSystem(Family.all(
    SkeletonComponent::class.java
).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val deadableComponent = entity.getComponent(DeadableComponent::class.java)
        if (!deadableComponent.dead) return

        engine.removeEntity(entity)
    }
}