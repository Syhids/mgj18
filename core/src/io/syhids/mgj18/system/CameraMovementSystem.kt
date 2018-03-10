package io.syhids.mgj18.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.OrthographicCamera
import io.syhids.mgj18.Hero
import io.syhids.mgj18.position

class CameraMovementSystem(val camera: OrthographicCamera) : EntitySystem() {
    override fun update(deltaTime: Float) {
        val hero = engine.entities.filterIsInstance(Hero::class.java).first()
        val pos = hero.position
        camera.position.x = pos.x
        camera.position.y = pos.y
    }
}