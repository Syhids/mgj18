package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import io.syhids.mgj18.*

class FollowEnemy : IteratingSystem(Family.all(
        PositionComponent::class.java,
        VelocityComponent::class.java
).get()) {
    private val position = component(PositionComponent::class)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val hero = engine.entities.first { it is Hero }
        val position = position.get(entity)

        hero.position

        //position.x += velocity.x * deltaTime
        //position.y += velocity.y * deltaTime
    }
}