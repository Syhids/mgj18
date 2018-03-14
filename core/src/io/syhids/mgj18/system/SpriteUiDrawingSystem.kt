package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.syhids.mgj18.PositionComponent
import io.syhids.mgj18.SpriteComponent
import io.syhids.mgj18.component
import io.syhids.mgj18.sprite

class SpriteUiDrawingSystem(
    private val batch: SpriteBatch,
    private val camera: OrthographicCamera
) : DebugSortedIteratingSystem(Family.all(
    PositionComponent::class.java,
    SpriteComponent::class.java
).get(), { e, e2 ->
    e.sprite.depth - e2.sprite.depth
}) {

    private val position = component(PositionComponent::class)
    private val sprite = component(SpriteComponent::class)

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
        val sprite = sprite.get(entity)

        if (!sprite.visible || sprite.img == null)
            return

        if (sprite.isUi.not()) return

        val position = position.get(entity)
        val spriteToDraw = sprite.sprite

        spriteToDraw.setAlpha(sprite.alpha)
        spriteToDraw.setScale(sprite.scale)
        spriteToDraw.setCenter(position.x, position.y)
        spriteToDraw.rotation = sprite.rotation

        spriteToDraw.draw(batch)
    }
}