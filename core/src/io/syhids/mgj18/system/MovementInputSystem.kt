package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Input.Keys
import io.syhids.mgj18.MoveableByKeyboardComponent
import io.syhids.mgj18.SoulCursor
import io.syhids.mgj18.anyKeyPressed
import io.syhids.mgj18.velocity

class MovementInputSystem : IteratingSystem(Family.all(
    MoveableByKeyboardComponent::class.java
).get()) {
    val POWER = 290f
    val SOUL_CURSOR_POWER = 1890f

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val moveableComponent = entity.getComponent(MoveableByKeyboardComponent::class.java)
        if (!moveableComponent.enabled) return

        moveVelocityOf(entity, deltaTime)
    }

    private fun moveVelocityOf(entity: Entity, deltaTime: Float) {
        val power = if (entity is SoulCursor) SOUL_CURSOR_POWER else POWER
        val velocity = entity.velocity
        when {
            anyKeyPressed(Keys.A, Keys.LEFT) -> velocity.x -= deltaTime * power
            anyKeyPressed(Keys.D, Keys.RIGHT) -> velocity.x += deltaTime * power
        }

        when {
            anyKeyPressed(Keys.W, Keys.UP) -> velocity.y += deltaTime * power
            anyKeyPressed(Keys.S, Keys.DOWN) -> velocity.y -= deltaTime * power
        }
    }
}