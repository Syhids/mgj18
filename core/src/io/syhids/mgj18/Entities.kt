package io.syhids.mgj18

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import ktx.math.vec2

val Entity.position: PositionComponent
    get() = getComponent(PositionComponent::class.java)

val Entity.velocity: VelocityComponent
    get() = getComponent(VelocityComponent::class.java)

val Entity.friction: FrictionComponent?
    get() = getComponent(FrictionComponent::class.java)

val Entity.sprite: SpriteComponent
    get() = getComponent(SpriteComponent::class.java)

val Entity.movement: MovableComponent
    get() = getComponent(MovableComponent::class.java)

val Entity.animation: AnimationComponent
    get() = getComponent(AnimationComponent::class.java)

val Entity.collider: ColliderComponent
    get() = getComponent(ColliderComponent::class.java)

fun yAlignBottom(tex: Texture, scale: Float = 1f): Float {
    return -(WORLD_HEIGHT / 2 - tex.height * scale / 2)
}

class Background : Entity() {
    init {
        val texture = Texture("bg.jpeg")
        add(PositionComponent(y = yAlignBottom(texture) + 0f))
        add(SpriteComponent(img = texture, depth = -100))
    }
}

class Boss : Entity() {
    init {
        val radius = 80f
        add(PositionComponent())
        add(PrimitiveDrawingComponent(Color.RED, radius))
    }
}

class Hero : Entity() {
    init {
        val texture = Texture("pj_final.png")
        val radius = 40f
        add(PositionComponent(x = 200f, y = 0f))
        add(SpriteComponent(img = texture, depth = 1))
        add(VelocityComponent())
        add(FrictionComponent(value = 0.1f))
        add(CircleColliderComponent(radius * 1.36f, canBeRepelled = false))
        add(PrimitiveDrawingComponent(Color(0f, 0.5f, 0.0f, 1f), radius))
        add(KeyboardAffectedComponent)
    }
}

class Enemy(initialX: Float = 0f, initialY: Float = 0f) : Entity() {
    init {
        val radius = 40f
        add(PositionComponent(x = initialX, y = initialY))
        add(VelocityComponent())
        add(CircleColliderComponent(radius * 0.65f))
        add(FrictionComponent(value = 0.13f))
        add(PrimitiveDrawingComponent(Color.GOLD, radius))
        add(EnemyComponent(velocityMultiplier = 400f))
    }
}

class Tomb : Entity() {
    init {
        val texture = Texture("Lapida.png")
        add(PositionComponent(x = -50f, y = 200f))
        add(SpriteComponent(img = texture, depth = -1))
        add(CanSpawnComponent())
    }
}

class Bullet : Entity() {
    init {
        val radius = 6f
        add(PositionComponent())
        add(PrimitiveDrawingComponent(Color.DARK_GRAY, radius))
        add(ShootComponent(vec2(1f, 0f)))
        add(VelocityComponent())
    }
}

class Menu : Entity() {
    init {
        add(MenuComponent())
        add(PositionComponent(y = 26f))
        add(SpriteComponent(img = Texture("titulo.png"), scale = 1f, visible = false, depth = 1000))
    }

    val playButtonArea = Rectangle(-575f, -138f, 370f, 140f)
    val exitButtonArea = Rectangle(-575f, -323f, 370f, 140f)

    fun isPlayButtonClicked(clickPos: Vector2): Boolean {
        return playButtonArea.contains(clickPos)
    }

    fun isExitButtonClicked(clickPos: Vector2): Boolean {
        return exitButtonArea.contains(clickPos)
    }
}