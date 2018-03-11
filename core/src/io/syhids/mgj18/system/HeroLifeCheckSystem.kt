package io.syhids.mgj18.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import io.syhids.mgj18.DeadableComponent
import io.syhids.mgj18.Hero
import io.syhids.mgj18.LifeComponent

class HeroLifeCheckSystem : EntitySystem() {
    override fun update(deltaTime: Float) {
        val hero = engine.entities.first { it is Hero } as Hero

        val dead = hero.getComponent(DeadableComponent::class.java)
        if (dead.hit) {
            dead.hit = false
            dead.hitSource = null

            val life = hero.getComponent(LifeComponent::class.java)
            life.remainingHits -= 1

            if (life.remainingHits <= 0) {
                Gdx.app.exit()
            }
        }
    }
}