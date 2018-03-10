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
            bullet.position.x = hero.position.x
            bullet.position.y = hero.position.y
            engine.addEntity(bullet)

            val dir = bullet.getComponent(ShootComponent::class.java).dir

            val power = 200
            bullet.velocity.x = dir.x * power
            bullet.velocity.y = dir.y * power

        }
    }
}
