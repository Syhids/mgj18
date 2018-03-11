package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import io.syhids.mgj18.DeadableComponent
import io.syhids.mgj18.Hero
import io.syhids.mgj18.Skeleton.SkeletonComponent
import io.syhids.mgj18.position

class SkeletonDeadSystem : DebugIteratingSystem(Family.all(
    SkeletonComponent::class.java
).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val deadableComponent = entity.getComponent(DeadableComponent::class.java)
        if (!deadableComponent.hit) {
            val hero = engine.entities.first { it is Hero } as Hero

            val POWER = 58
            if (hero.position.toVec2.dst2(entity.position.toVec2) < POWER * POWER) {
                val life = hero.getComponent(DeadableComponent::class.java)
                life.hit = true
                life.hitSource = DeadableComponent.HitSource.Bullet
                engine.removeEntity(entity)
            }

            return
        }

        engine.removeEntity(entity)
    }
}