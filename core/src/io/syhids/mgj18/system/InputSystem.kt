package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Input.Keys
import io.syhids.mgj18.KeyboardAffectedComponent
import io.syhids.mgj18.PositionComponent
import io.syhids.mgj18.anyKeyPressed
import io.syhids.mgj18.component

class InputSystem : IteratingSystem(Family.all(
    PositionComponent::class.java,
    KeyboardAffectedComponent::class.java
).get()) {
    private val position = component(PositionComponent::class)

    val POWER = 50f

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = position.get(entity)

        when {
            anyKeyPressed(Keys.A, Keys.LEFT) -> position.x -= deltaTime * POWER
            anyKeyPressed(Keys.D, Keys.RIGHT) -> position.x += deltaTime * POWER
        }

        when {
            anyKeyPressed(Keys.W, Keys.UP) -> position.y += deltaTime * POWER
            anyKeyPressed(Keys.S, Keys.DOWN) -> position.y -= deltaTime * POWER
        }
    }
}