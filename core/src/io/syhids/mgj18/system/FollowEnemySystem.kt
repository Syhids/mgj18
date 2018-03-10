package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import io.syhids.mgj18.*
import ktx.math.times

class FollowEnemySystem : IteratingSystem(Family.all(
        EnemyComponent::class.java

).get()) {
    private val position = component(PositionComponent::class)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val hero = engine.entities.first { it is Hero }
        val enemy = entity
        val enemyPosition = position.get(enemy)


        val direction = (hero.position - enemy.position).nor()
        enemyPosition += direction * 2



        //position.x += velocity.x * deltaTime
        //position.y += velocity.y * deltaTime
    }
}