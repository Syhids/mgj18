package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import io.syhids.mgj18.CanSpawnComponent
import io.syhids.mgj18.Enemy
import io.syhids.mgj18.component
import io.syhids.mgj18.position

class SpawnEnemySystem : IteratingSystem(Family.all(
        CanSpawnComponent::class.java
).get()) {
    // private val position = component(PositionComponent::class)
    private val canSpawn = component(CanSpawnComponent::class)
    var accTime = 0f

    override fun processEntity(entity: Entity, deltaTime: Float) {

        val canSpawnComponent = canSpawn.get(entity)
        canSpawnComponent.accTime += deltaTime
        val minTime = 2
        if (canSpawnComponent.accTime >= minTime) {
            val enemy = Enemy()
            enemy.position.x = entity.position.x
            enemy.position.y = entity.position.y
            engine.addEntity(enemy)
            canSpawnComponent.accTime -= minTime
        }


    }

    override fun update(deltaTime: Float) {
        accTime += deltaTime
        super.update(deltaTime)
    }
}