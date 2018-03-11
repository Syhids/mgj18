package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import io.syhids.mgj18.DeadableComponent
import io.syhids.mgj18.Skeleton.SkeletonComponent

class SkeletonDeadSystem : DebugIteratingSystem(Family.all(
    SkeletonComponent::class.java
).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val deadableComponent = entity.getComponent(DeadableComponent::class.java)
        if (!deadableComponent.hit) return

        engine.removeEntity(entity)
    }
}