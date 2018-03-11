package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import io.syhids.mgj18.Boss
import io.syhids.mgj18.GameState
import io.syhids.mgj18.currentGameState
import io.syhids.mgj18.velocity

class FirstSceneAnimationSystem : DebugIteratingSystem(Family.all(
        Boss.BossLifeComponent::class.java
).get()) {
    var accTime = 0f
    var bossMoving = false

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val boss = entity
        accTime += deltaTime

        if (accTime > 3f) {
            bossMoving = true
        }

        if (bossMoving) {
            val velocity = boss.velocity
            velocity.y += deltaTime * 20f
        }

        if (accTime >= 12f) {
            currentGameState = GameState.GameMode
        }
//        when (2f = time) {
//            -> boss.position.y += -5f
//            -> boss.position.x += 50f
//        }
    }
}
