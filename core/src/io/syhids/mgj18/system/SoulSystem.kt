package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import io.syhids.mgj18.*
import io.syhids.mgj18.component.SoulComponent
import io.syhids.mgj18.component.SoulComponent.State

class SoulSystem : EntitySystem() {
    val soulCache = cacheOfComponent(SoulComponent::class)

    val soulCursor: SoulCursor
        get () = engine.entities.first { it is SoulCursor } as SoulCursor

    val hero: Hero
        get() = engine.entities.first { it is Hero } as Hero

    var state: SoulComponent.State
        get() = soul.state
        set(value) {
            soul.state = value
        }

    val soul: SoulComponent
        get() = soulCache.get(hero)

    override fun update(deltaTime: Float) {
        val soulEntity = engine.entities.first { it is SoulCursor } as SoulCursor

        soulEntity.sprite.visible = soul.state == State.SelectingSoul

        when (soul.state) {
            State.NoSoul -> {
                if (Gdx.input.isKeyJustPressed(Keys.C)) {
                    state = State.SelectingSoul
                    disableMovementForEveryone()
                    soulCursor.setMovementEnabled(true)
                    soulCursor.position.set(hero.position.x, hero.position.y)
                }
            }
            State.SelectingSoul -> {
                when {
                    Gdx.input.isKeyJustPressed(Keys.SPACE) ||
                        Gdx.input.isKeyJustPressed(Keys.C) -> {
                        val cursorPosition = soulCursor.position.toVec2
                        val entityCaptured = engine.entities
                            .map { it to cursorPosition.dst2(it.position.toVec2) }
                            .sortedByDescending { -it.second }
                            .first { it.first.hasComponent<EnemyComponent>() }

                        if (entityCaptured.second > 40 * 40) {
                            state = State.NoSoul
                            disableMovementForEveryone()
                            hero.setMovementEnabled(true)
                            return
                        }

                        val newState = State.ControllingSoul(entityCaptured.first)
                        state = newState
                        disableMovementForEveryone()
                        newState.entity.setMovementEnabled(true)
                    }
                    Gdx.input.isKeyJustPressed(Keys.ESCAPE) -> {
                        state = State.NoSoul
                        disableMovementForEveryone()
                        hero.setMovementEnabled(true)
                    }
                }
            }
            is SoulComponent.State.ControllingSoul -> {
                if (Gdx.input.isKeyJustPressed(Keys.SPACE) ||
                    Gdx.input.isKeyJustPressed(Keys.ESCAPE) ||
                    Gdx.input.isKeyJustPressed(Keys.C)
                ) {
                    state = State.NoSoul
                    disableMovementForEveryone()
                    hero.setMovementEnabled(true)
                }
            }
        }
    }

    private fun disableMovementForEveryone() {
        engine.entities.filter { it.hasComponent<MoveableByKeyboardComponent>() }
            .map { it.getComponent(MoveableByKeyboardComponent::class.java) }
            .forEach { it.enabled = false }
    }

    private fun Entity.setMovementEnabled(enabled: Boolean) {
        getComponent(MoveableByKeyboardComponent::class.java).enabled = enabled
    }
}