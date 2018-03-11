package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.syhids.mgj18.*

class SpriteDrawingSystem(
    private val batch: SpriteBatch,
    private val camera: OrthographicCamera
) : SortedIteratingSystem(Family.all(
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

        //XXX: Hacky
        if (entity is Menu) {
            Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        }

        val position = position.get(entity)
        val spriteToDraw = sprite.sprite

        spriteToDraw.setAlpha(sprite.alpha)
        spriteToDraw.setScale(sprite.scale)
        spriteToDraw.setCenter(position.x, position.y)
        spriteToDraw.rotation = sprite.rotation

        if (entity is BackgroundClouds)
            batch.draw(sprite.img, (-sprite.width).toFloat(), (-sprite.height).toFloat(),
                (accDelta * 14).toInt(), 0, sprite.width * 2, sprite.height * 2)
        else
            spriteToDraw.draw(batch)
    }
}