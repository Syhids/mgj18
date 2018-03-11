package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Rectangle
import io.syhids.mgj18.BackgroundClouds
import io.syhids.mgj18.PositionComponent
import io.syhids.mgj18.component

class CleanEntitiesOutsideTheWorldSystem(
    val rect: Rectangle
) : IteratingSystem(Family.all(
    PositionComponent::class.java
).get()) {
    private val position = component(PositionComponent::class)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (entity is BackgroundClouds)
            return

        val position = position.get(entity)

        if (!rect.contains(position.x, position.y)) {
            println("Cleaning $entity")
            engine.removeEntity(entity)
        }
    }
}