package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Input.Keys
import io.syhids.mgj18.*

class MovementSpriteSystem : IteratingSystem(Family.all(
        SpriteComponent::class.java,
        AnimationComponent::class.java,
        MoveableByKeyboardComponent::class.java
).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (entity !is Hero) return

        if (entity.getComponent(MoveableByKeyboardComponent::class.java).enabled.not())
            return

        val animation = entity.animation

        when {
        //falta poner la animacion que toca a cada
            anyKeyPressed(Keys.A, Keys.LEFT) -> {
                animation.state = AnimationComponent.State.Playing(-1)
                animation.playIfNotAlready(heroLeftAnimation)
            }
            anyKeyPressed(Keys.D, Keys.RIGHT) -> {
                animation.state = AnimationComponent.State.Playing(-1)
                animation.playIfNotAlready(heroRightAnimation)
            }
//                anyKeyPressed(Keys.W, Keys.UP) ->
            anyKeyPressed(Keys.S, Keys.DOWN) -> {
                animation.state = AnimationComponent.State.Playing(-1)
                animation.playIfNotAlready(heroDownAnimation)
            }
            else -> animation.state = AnimationComponent.State.Paused
        }
    }
}