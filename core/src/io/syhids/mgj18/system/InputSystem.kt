package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Input.Keys
import io.syhids.mgj18.KeyboardAffectedComponent
import io.syhids.mgj18.VelocityComponent
import io.syhids.mgj18.anyKeyPressed
import io.syhids.mgj18.component

class InputSystem : IteratingSystem(Family.all(
    VelocityComponent::class.java,
    KeyboardAffectedComponent::class.java
).get()) {
    private val velocity = component(VelocityComponent::class)

    val POWER = 50f

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val velocity = velocity.get(entity)

        when {
            anyKeyPressed(Keys.A, Keys.LEFT) -> velocity.x -= deltaTime * POWER
            anyKeyPressed(Keys.D, Keys.RIGHT) -> velocity.x += deltaTime * POWER
        }

        when {
            anyKeyPressed(Keys.W, Keys.UP) -> velocity.y += deltaTime * POWER
            anyKeyPressed(Keys.S, Keys.DOWN) -> velocity.y -= deltaTime * POWER
        }
    }
}