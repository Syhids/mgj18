package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import io.syhids.mgj18.*

class SpawnEnemySystem : DebugIteratingSystem(Family.all(
    CanSpawnComponent::class.java
).get()) {
    private val canSpawn = component(CanSpawnComponent::class)
    var accTime = 0f

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val canSpawnComponent = canSpawn.get(entity)
        canSpawnComponent.accTime += deltaTime
        val minTime = 0

        if (canSpawnComponent.accTime >= minTime) {

            val time = System.currentTimeMillis()
            val enemy = if (Math.random() > 0.22f)
                Skeleton()
            else
                AltarBoy()

            val tookTime = System.currentTimeMillis() - time

            if (tookTime > 1)
                println("Single spawn took $tookTime ms")

            enemy.position.x = entity.position.x
            enemy.position.y = entity.position.y
            engine.addEntity(enemy)
            canSpawnComponent.accTime -= 5 + Math.random().toFloat() * 12
        }
    }

    override fun update(deltaTime: Float) {
        accTime += deltaTime
        super.update(deltaTime)
    }
}