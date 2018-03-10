package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import io.syhids.mgj18.AnimationComponent
import io.syhids.mgj18.SpriteComponent
import io.syhids.mgj18.animation
import io.syhids.mgj18.sprite

class MovementSpriteSystem : IteratingSystem(Family.all(
        SpriteComponent::class.java,
        AnimationComponent::class.java
).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val animation = entity.animation
        val hero = entity.sprite

        animation.step(deltaTime)
        animation.updateCurrentAnimation()

//        when {
//        //falta poner la animacion que toca a cada
//            anyKeyPressed(Keys.A, Keys.LEFT) ->
//                anyKeyPressed(Keys.D, Keys.RIGHT)  ->
//                anyKeyPressed(Keys.W, Keys.UP) ->
//                anyKeyPressed(Keys.S, Keys.DOWN) ->
//        }
    }
}