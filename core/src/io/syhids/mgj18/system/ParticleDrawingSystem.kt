package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.syhids.mgj18.Particle.ParticleDrawingComponent
import io.syhids.mgj18.PositionComponent
import io.syhids.mgj18.component
import io.syhids.mgj18.particle

class ParticleDrawingSystem(
    private val batch: SpriteBatch,
    private val camera: OrthographicCamera
) : DebugSortedIteratingSystem(Family.all(
    PositionComponent::class.java,
    ParticleDrawingComponent::class.java
).get(), { e, e2 ->
    e.particle.depth - e2.particle.depth
}) {

    private val position = component(PositionComponent::class)
    private val particle = component(ParticleDrawingComponent::class)

    var accDelta = 0f

    override fun update(deltaTime: Float) {
        camera.update()

        accDelta += deltaTime

        batch.enableBlending()
        batch.begin()
        batch.projectionMatrix = camera.combined
        super.update(deltaTime)
        batch.end()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val particle = particle.get(entity)
        val position = position.get(entity)
        particle.effect.setPosition(position.x, position.y)
        particle.effect.draw(batch, deltaTime)

        if (particle.effect.isComplete){
            particle.effect.free()
        }
    }
}