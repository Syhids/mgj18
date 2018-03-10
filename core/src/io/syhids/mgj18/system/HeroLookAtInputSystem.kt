package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Input.Keys
import io.syhids.mgj18.LookAtComponent
import io.syhids.mgj18.LookAtComponent.LookAtDirection
import io.syhids.mgj18.anyKeyPressed
import io.syhids.mgj18.component

class HeroLookAtInputSystem : IteratingSystem(Family.all(
        LookAtComponent::class.java

).get()) {

    private val lookAtCache = component(LookAtComponent::class)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val lookAt = lookAtCache.get(entity)

        val direction = when {
            anyKeyPressed(Keys.A, Keys.LEFT) -> LookAtDirection.Left
            anyKeyPressed(Keys.D, Keys.RIGHT) -> LookAtDirection.Right
            anyKeyPressed(Keys.W, Keys.UP) -> LookAtDirection.Up
            anyKeyPressed(Keys.S, Keys.DOWN) -> LookAtDirection.Down
            else -> null
        }
        if (direction != null)
            lookAt.dir = direction

    }
}