package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Input
import io.syhids.mgj18.*
import io.syhids.mgj18.LookAtComponent.LoockAtDirection

class ShootingInputSystem : IteratingSystem(Family.all(
    KeyboardAffectedComponent::class.java
).get()) {
    val BULLET_SPEED = 200

    val shootCache = cacheOfComponent(ShootComponent::class)
    val lookAtCache = cacheOfComponent(LookAtComponent::class)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (!keyJustPressed(Input.Keys.SPACE)) return

        val hero = engine.entities.first { it is Hero }
        val bullet = Bullet()

        val shootComponent = shootCache.get(bullet)
        val lookAt = lookAtCache.get(hero)

        val shootDirection = shootComponent.dir

        when (lookAt.dir) {
            LoockAtDirection.Left -> shootDirection.set(-1f, 0f)
            LoockAtDirection.Right -> shootDirection.set(1f, 0f)
            LoockAtDirection.Up -> shootDirection.set(0f, 1f)
            LoockAtDirection.Doown -> shootDirection.set(0f, -1f)
        }
        val distance = 50
        val bulletPos = bullet.position
        when (lookAt.dir) {
            LoockAtDirection.Left -> bulletPos.set(hero.position.x - distance, hero.position.y)
            LoockAtDirection.Right -> bulletPos.set(hero.position.x + distance, hero.position.y)
            LoockAtDirection.Up -> bulletPos.set(hero.position.x, hero.position.y + distance)
            LoockAtDirection.Doown -> bulletPos.set(hero.position.x, hero.position.y - distance)
        }
        bullet.velocity.x = shootDirection.x * BULLET_SPEED
        bullet.velocity.y = shootDirection.y * BULLET_SPEED

        engine.addEntity(bullet)
    }
}
