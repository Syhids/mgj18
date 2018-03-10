package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20.*
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.syhids.mgj18.PositionComponent
import io.syhids.mgj18.PrimitiveDrawingComponent
import io.syhids.mgj18.component
import ktx.app.copy

class PrimitiveDrawingSystem(
    private val shapeRenderer: ShapeRenderer,
    private val camera: OrthographicCamera
) : IteratingSystem(Family.all(
    PositionComponent::class.java,
    PrimitiveDrawingComponent::class.java
).get()) {
    private val position = component(PositionComponent::class)
    private val primitive = component(PrimitiveDrawingComponent::class)

    override fun update(deltaTime: Float) {
        camera.update()

        Gdx.gl.glEnable(GL_BLEND);
        Gdx.gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin()
        shapeRenderer.projectionMatrix = camera.combined
        super.update(deltaTime)
        shapeRenderer.end()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val primitive = primitive.get(entity)
        val position = position.get(entity)

        shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = primitive.color.copy(alpha = 0.3f)
        val shape = primitive.shape
        when (shape) {
            is PrimitiveDrawingComponent.Shape.Circle ->
                shapeRenderer.circle(position.x, position.y, shape.radius)
            is PrimitiveDrawingComponent.Shape.Rectangle ->
                shapeRenderer.rect(position.x, position.y, shape.width.toFloat(), shape.height.toFloat())
        }
    }
}