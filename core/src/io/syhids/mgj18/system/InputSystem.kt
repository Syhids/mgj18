package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Input
import io.syhids.mgj18.KeyboardAffectedComponent
import io.syhids.mgj18.PositionComponent
import io.syhids.mgj18.component
import io.syhids.mgj18.keyPressed

class InputSystem : IteratingSystem(Family.all(
    PositionComponent::class.java,
    KeyboardAffectedComponent::class.java
).get()) {
    private val position = component(PositionComponent::class)

    val POWER = 50f

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = position.get(entity)

        when {
            keyPressed(Input.Keys.A) -> position.x -= deltaTime * POWER
            keyPressed(Input.Keys.D) -> position.x += deltaTime * POWER
        }

        when {
            keyPressed(Input.Keys.W) -> position.y += deltaTime * POWER
            keyPressed(Input.Keys.S) -> position.y -= deltaTime * POWER
        }
    }
}