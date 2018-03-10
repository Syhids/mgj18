package io.syhids.mgj18

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import io.syhids.mgj18.component.SoulComponent
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
        val texture = Texture(assetOf("pared.png"))
        add(PositionComponent())
        add(SpriteComponent(img = texture, depth = -100, scale = 0.5f))
    }
}

class Boss : Entity() {
    init {
        val radius = 80f
        add(PositionComponent())
        add(PrimitiveDrawingComponent(PrimitiveDrawingComponent.Shape.Circle(radius), Color.RED))
    }
}

val heroLeftAnimation = Animation(listOf(
        Frame("hero/leftAnimation/1.png", 200),
        Frame("hero/leftAnimation/2.png", 200),
        Frame("hero/leftAnimation/3.png", 200),
        Frame("hero/leftAnimation/4.png", 200),
        Frame("hero/leftAnimation/5.png", 200),
        Frame("hero/leftAnimation/6.png", 200)
))
val heroDownAnimation = Animation(listOf(
        Frame("hero/downAnimation/1.png", 200),
        Frame("hero/downAnimation/2.png", 200),
        Frame("hero/downAnimation/3.png", 200),
        Frame("hero/downAnimation/4.png", 200)
))

fun assetOf(asset: String) = Gdx.files.internal("assets/$asset")


class Hero : Entity() {

    init {
        val texture = Texture(assetOf("pj_final.png"))
        val radius = 30f
        add(PositionComponent(x = 200f, y = 0f))
        add(SpriteComponent(img = texture, depth = 1, scale = radius * 0.0028f))
        add(VelocityComponent())
        add(FrictionComponent(value = 0.1f))
        add(CircleColliderComponent(radius * 1f, canBeRepelled = false))
        add(PrimitiveDrawingComponent(PrimitiveDrawingComponent.Shape.Circle(radius), Color(0f, 0.5f, 0.0f, 1f)))
        add(SoulComponent())
        add(KeyboardAffectedComponent)
        add(MoveableByKeyboardComponent(enabled = true))
        add(LookAtComponent())
        add(AnimationComponent(heroDownAnimation))
    }
}

class Enemy(initialX: Float = 0f, initialY: Float = 0f) : Entity() {
    companion object {
        val texture by lazy { Texture(assetOf("pj_final.png")) }
    }

    init {
        val radius = 18f
        add(SpriteComponent(img = texture, depth = 0, scale = radius * 0.0030f))
        add(PositionComponent(x = initialX, y = initialY))
        add(VelocityComponent())
        add(CircleColliderComponent(radius * 0.75f))
        add(FrictionComponent(value = 0.13f))
        add(PrimitiveDrawingComponent(PrimitiveDrawingComponent.Shape.Circle(radius), Color.GOLD))
        add(EnemyComponent(velocityMultiplier = 80f))
        add(MoveableByKeyboardComponent(enabled = false))
    }
}

class Tomb : Entity() {
    init {
        val texture = Texture(assetOf("Lapida.png"))
        add(PositionComponent(x = -50f, y = 200f))
        add(SpriteComponent(img = texture, depth = -1, scale = 0.25f))
        add(PrimitiveDrawingComponent(PrimitiveDrawingComponent.Shape.Circle(20f), Color.BLUE))
        add(CanSpawnComponent())
    }
}

class Bullet : Entity() {
    companion object {
        val texture by lazy { Texture(assetOf("Bala.png")) }
    }

    init {
        val radius = 6f
        add(SpriteComponent(img = texture, depth = 0, scale = 0.33f))
        add(PositionComponent())
        add(PrimitiveDrawingComponent(PrimitiveDrawingComponent.Shape.Circle(radius), Color.DARK_GRAY))
        add(ShootComponent(vec2(1f, 0f)))
        add(VelocityComponent())
    }
}

class Life : Entity() {
    init {
        val texture = Texture(assetOf("Vida.png"))
        val life = 1f
        add(LifeHeroComponent(life))
        add(PositionComponent(x = -530f, y = -300f))
        add(SpriteComponent(img = texture, depth = -1, scale = 3f))
    }
}

class Soul : Entity() {
    init {
        val texture = Texture(assetOf("Bala.png"))
        add(PositionComponent(x = 530f, y = -300f))
        add(SpriteComponent(img = texture, depth = -1, scale = 3f))
    }
}

class SoulCursor : Entity() {
    companion object {
        val texture by lazy { Texture(assetOf("Bala.png")) }
    }

    init {
        val radius = 8f

        add(SpriteComponent(img = texture, depth = 5, scale = 0.38f, visible = false))
        add(PositionComponent())
        add(PrimitiveDrawingComponent(PrimitiveDrawingComponent.Shape.Circle(radius), Color.FIREBRICK))
        add(FrictionComponent(0.8f))
        add(VelocityComponent())
        add(CursorComponent())
        add(MoveableByKeyboardComponent(enabled = false))
    }

    class CursorComponent : Component
}

class Menu : Entity() {
    init {
        add(MenuComponent())
        add(PositionComponent(y = 26f))
        add(SpriteComponent(img = Texture(assetOf("titulo.png")), scale = 1f, visible = false, depth = 1000))
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