package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import io.syhids.mgj18.LifeHeroComponent
import io.syhids.mgj18.component


class LifeDrawSystem : DebugIteratingSystem(Family.all(
        LifeHeroComponent::class.java
).get()) {
    //    private val position = component(PositionComponent::class)
//    private val velocity = component(VelocityComponent::class)
    private val lifeHeroComponent = component(LifeHeroComponent::class)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        //volverla rellenarla con "algo"


    }
}
