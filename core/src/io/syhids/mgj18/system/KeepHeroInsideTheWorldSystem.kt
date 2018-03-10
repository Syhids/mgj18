package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import io.syhids.mgj18.KeyboardAffectedComponent
import io.syhids.mgj18.PositionComponent
import io.syhids.mgj18.VelocityComponent
import io.syhids.mgj18.cacheOfComponent
import ktx.math.times

class KeepHeroInsideTheWorldSystem(
    val rect: Rectangle
) : IteratingSystem(Family.all(
    KeyboardAffectedComponent::class.java
).get()) {
    private val positionCache = cacheOfComponent(PositionComponent::class)
    private val velocityCache = cacheOfComponent(VelocityComponent::class)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = positionCache.get(entity)
        val velocity = velocityCache.get(entity)

        if (!rect.contains(position.x, position.y)) {
            val centerOfTheWorld = Vector2(0f, 0f)
            val dir = (centerOfTheWorld - position).nor()

            position += dir * 50f * deltaTime
            velocity *= 0.6f
        }
    }
}