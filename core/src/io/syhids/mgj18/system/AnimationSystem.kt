package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import io.syhids.mgj18.AnimationComponent
import io.syhids.mgj18.SpriteComponent
import io.syhids.mgj18.animation
import io.syhids.mgj18.sprite

class AnimationSystem : DebugIteratingSystem(Family.all(
    SpriteComponent::class.java,
    AnimationComponent::class.java
).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val animation = entity.animation
        val sprite = entity.sprite

        animation.step(deltaTime)
        animation.updateCurrentAnimation()

        sprite.img = animation.getCurrentAnimation()
    }
}