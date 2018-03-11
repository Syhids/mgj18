package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import io.syhids.mgj18.*
import io.syhids.mgj18.component.SoulComponent
import io.syhids.mgj18.component.SoulComponent.State

class SoulSystem : DebugEntitySystem() {
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

    override fun onUpdate(deltaTime: Float) {
        val soulEntity = engine.entities.first { it is SoulCursor } as SoulCursor

        val currentState = soul.state
        soulEntity.sprite.visible = currentState == State.SelectingSoul

        when (currentState) {
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
                            .filter { it.first !is Boss }
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
                    val deadable = currentState.entity.getComponent(DeadableComponent::class.java)
                    deadable.hit = true
                    deadable.hitSource = DeadableComponent.HitSource.SoulLeaving
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