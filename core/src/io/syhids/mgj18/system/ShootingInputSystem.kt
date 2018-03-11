package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.Input
import io.syhids.mgj18.*
import io.syhids.mgj18.Hero.ShotgunDelayComponent
import io.syhids.mgj18.LookAtComponent.LookAtDirection
import io.syhids.mgj18.component.SoulComponent

class ShootingInputSystem : DebugIteratingSystem(Family.all(
    KeyboardAffectedComponent::class.java
).get()) {
    val BULLET_SPEED = 200

    val shootCache = cacheOfComponent(ShootComponent::class)
    val lookAtCache = cacheOfComponent(LookAtComponent::class)
    val shotgunDelayCache = cacheOfComponent(ShotgunDelayComponent::class)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val hero = engine.entities.first { it is Hero }
        val shotgun = shotgunDelayCache.get(hero)
        shotgun.lastShootAcc -= deltaTime

        if (!keyJustPressed(Input.Keys.SPACE)) return

        val soulSystem = engine.getSystem(SoulSystem::class.java)

        if (soulSystem.state == SoulComponent.State.SelectingSoul) return
        if (soulSystem.state is SoulComponent.State.ControllingSoul) return
        if (shotgun.lastShootAcc > 0f) return

        shotgun.lastShootAcc = 1.9f

        val bullet = Bullet()
        val shootComponent = shootCache.get(bullet)
        val lookAt = lookAtCache.get(hero)

        val shootDirection = shootComponent.dir

        when (lookAt.dir) {
            LookAtDirection.Left -> shootDirection.set(-1f, 0f)
            LookAtDirection.Right -> shootDirection.set(1f, 0f)
            LookAtDirection.Up -> shootDirection.set(0f, 1f)
            LookAtDirection.Down -> shootDirection.set(0f, -1f)
        }
        val distance = 50
        val bulletPos = bullet.position
        when (lookAt.dir) {
            LookAtDirection.Left -> bulletPos.set(hero.position.x - distance, hero.position.y)
            LookAtDirection.Right -> bulletPos.set(hero.position.x + distance, hero.position.y)
            LookAtDirection.Up -> bulletPos.set(hero.position.x, hero.position.y + distance)
            LookAtDirection.Down -> bulletPos.set(hero.position.x, hero.position.y - distance)
        }
        bullet.velocity.x = shootDirection.x * BULLET_SPEED
        bullet.velocity.y = shootDirection.y * BULLET_SPEED

        engine.addEntity(bullet)
        engine.addEntity(Particle(
            bullet.position.toVec2,
            explosionEffect,
            lifetime = 1f,
            scale = 0.2f
        ))
    }
}
