package io.syhids.mgj18.system

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.math.Circle
import io.syhids.mgj18.*
import ktx.ashley.has
import ktx.math.times

inline fun <reified T : Component> Entity.hasComponent() = has(ComponentMapper.getFor(T::class.java))

class CollisionAvoidSystem : DebugIteratingSystem(Family.all(
    PositionComponent::class.java,
    VelocityComponent::class.java,
    CircleColliderComponent::class.java
).get()) {
    val REPEL_POWER = 15f

    private val position = component(PositionComponent::class)
    private val velocity = component(VelocityComponent::class)
    private val circleColliderCache = component(CircleColliderComponent::class)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = position.get(entity)
        val velocity = velocity.get(entity)
        val circleCollider = circleColliderCache.get(entity)

        if (!circleCollider.canBeRepelled) return

        val otherColliders = engine.entities
            .filterNot { it == entity }
            .filter { it.hasComponent<CircleColliderComponent>() }
            .map {
                it to Circle(
                    it.position.x,
                    it.position.y,
                    it.getComponent(CircleColliderComponent::class.java).radius
                )
            }

        val myCircle = Circle(
            position.x,
            position.y,
            circleCollider.radius
        )

        otherColliders.forEach { (otherEntity, otherCollider) ->
            val willOverlap = false
            val overlaps = otherCollider.overlaps(myCircle)

            when {
                willOverlap -> {
                    velocity.x = 0f
                    velocity.y = 0f
                }
                overlaps -> {
                    val repelDir = (position - otherEntity.position).nor()
                    val repelAmount = repelDir * REPEL_POWER
                    velocity += repelAmount
                }
            }
        }
    }
}