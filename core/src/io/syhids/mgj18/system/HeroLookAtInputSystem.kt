package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Input.Keys
import io.syhids.mgj18.LookAtComponent
import io.syhids.mgj18.LookAtComponent.LoockAtDirection
import io.syhids.mgj18.anyKeyPressed
import io.syhids.mgj18.component

class HeroLookAtInputSystem : IteratingSystem(Family.all(
        LookAtComponent::class.java

).get()) {

    private val lookAtCache = component(LookAtComponent::class)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val lookAt = lookAtCache.get(entity)

        val direction = when {
            anyKeyPressed(Keys.A, Keys.LEFT) -> LoockAtDirection.Left
            anyKeyPressed(Keys.D, Keys.RIGHT) -> LoockAtDirection.Right
            anyKeyPressed(Keys.W, Keys.UP) -> LoockAtDirection.Up
            anyKeyPressed(Keys.S, Keys.DOWN) -> LoockAtDirection.Doown
            else -> null
        }
        if (direction != null)
            lookAt.dir = direction

    }
}