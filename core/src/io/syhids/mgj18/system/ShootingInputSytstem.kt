package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Input
import io.syhids.mgj18.*

class ShootingInputSytstem : IteratingSystem(Family.all(
        ShootComponent::class.java

).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {

        val bullet = Bullet()
        val hero = engine.entities.first { it is Hero }


        if (keyJustPressed(Input.Keys.SPACE)) {

            engine.addEntity(bullet)

            val shootComponent = bullet.getComponent(ShootComponent::class.java)

            val lookAt = hero.getComponent(LookAtComponent::class.java)

            val power = 200
            when (lookAt.dir) {
                LookAtComponent.LoockAtDirection.Left -> shootComponent.dir.set(-1f, 0f)
                LookAtComponent.LoockAtDirection.Right -> shootComponent.dir.set(1f, 0f)
                LookAtComponent.LoockAtDirection.Up -> shootComponent.dir.set(0f, 1f)
                LookAtComponent.LoockAtDirection.Doown -> shootComponent.dir.set(0f, -1f)
            }
            val distance = 50
            when (lookAt.dir) {
                LookAtComponent.LoockAtDirection.Left -> bullet.position.set(hero.position.x - distance, hero.position.y)
                LookAtComponent.LoockAtDirection.Right -> bullet.position.set(hero.position.x + distance, hero.position.y)
                LookAtComponent.LoockAtDirection.Up -> bullet.position.set(hero.position.x, hero.position.y + distance)
                LookAtComponent.LoockAtDirection.Doown -> bullet.position.set(hero.position.x, hero.position.y - distance)
            }
            bullet.velocity.x = shootComponent.dir.x * power
            bullet.velocity.y = shootComponent.dir.y * power

        }
    }
}
