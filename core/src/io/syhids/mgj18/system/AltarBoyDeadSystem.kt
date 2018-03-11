package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import io.syhids.mgj18.AltarBoy
import io.syhids.mgj18.DeadableComponent
import io.syhids.mgj18.DeadableComponent.HitSource
import io.syhids.mgj18.EnemyComponent
import io.syhids.mgj18.position

class AltarBoyDeadSystem : IteratingSystem(Family.all(
    AltarBoy.AltarBoyComponent::class.java
).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val deadable = entity.getComponent(DeadableComponent::class.java)
        if (!deadable.hit) return

        val deadPos = entity.position.toVec2

        engine.entities
            .filter { it.hasComponent<EnemyComponent>() }
            .map { it to it.position.toVec2.dst2(deadPos) }
            .sortedByDescending { -it.second }
            .filter { it.second < 100 * 100 }
            .map { it.first }
            .forEach {
                val deadable = it.getComponent(DeadableComponent::class.java)
                deadable.hit = true
                deadable.hitSource = HitSource.Explosion
            }

        engine.removeEntity(entity)
    }
}