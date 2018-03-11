package io.syhids.mgj18.system

import io.syhids.mgj18.*
import io.syhids.mgj18.Boss.BossLifeComponent
import io.syhids.mgj18.Boss.BossStateComponent
import io.syhids.mgj18.Boss.BossStateComponent.Stage
import io.syhids.mgj18.DeadableComponent.HitSource

class BossStageSystem : DebugEntitySystem() {
    override fun onUpdate(deltaTime: Float) {
        val boss = engine.entities.first { it is Boss } as Boss

        val deadable = boss.getComponent(DeadableComponent::class.java)

        if (deadable.hit) {
            deadable.hit = false
            deadable.hitSource = null
            onHit(boss, deadable.hitSource)
        }
    }

    fun onHit(boss: Boss, hitSource: DeadableComponent.HitSource?) {
        val bossLife = boss.getComponent(BossLifeComponent::class.java)
        val bossStage = boss.getComponent(BossStateComponent::class.java)

        when (bossStage.stage) {
            Boss.BossStateComponent.Stage.One -> {
                bossLife.health -= 1
                onBossLifeReduced(boss, hitSource)
            }
            Boss.BossStateComponent.Stage.Two -> {
                if (hitSource == HitSource.Explosion) {
                    bossLife.health -= 1
                    onBossLifeReduced(boss, hitSource)
                }
            }
            Boss.BossStateComponent.Stage.Three -> {
                if (hitSource == HitSource.Explosion) {
                    bossLife.health -= 1
                    onBossLifeReduced(boss, hitSource)
                }
            }
        }

        if (bossLife.health <= 0) {
            when (bossStage.stage) {
                Stage.One -> {
                    bossStage.stage = Stage.Two
                    bossLife.health = 3
                }
                Stage.Two -> {
                    bossStage.stage = Stage.Three
                    bossLife.health = 3
                }
                Stage.Three -> {
                    throw RuntimeException()
                }
            }
        }
    }

    private fun onBossLifeReduced(boss: Boss, hitSource: HitSource?) {
        val scaleBy = when (hitSource) {
            DeadableComponent.HitSource.Bullet -> 1f
            DeadableComponent.HitSource.Explosion -> 10f
            DeadableComponent.HitSource.SoulLeaving -> 1f
            null -> 1f
        }
        engine.addEntity(Particle(boss.position.toVec2, explosionEffect, 1f, scale = scaleBy))
    }
}