package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import io.syhids.mgj18.*
import io.syhids.mgj18.DeadableComponent.HitSource

class AltarBoyDeadSystem : DebugIteratingSystem(Family.all(
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
            .filter { it.second < 120 * 120 }
            .map { it.first }
            .forEach {
                if (it is Boss)
                    println("Boss hit by explosion")
                val deadable = it.getComponent(DeadableComponent::class.java)
                deadable.hit = true
                deadable.hitSource = HitSource.Explosion
            }

        engine.removeEntity(entity)
        engine.addEntity(Particle(entity.position.toVec2, explosionEffect, 1f))
    }
}