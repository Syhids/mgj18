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

val Entity.particle: Particle.ParticleDrawingComponent
    get() = getComponent(Particle.ParticleDrawingComponent::class.java)

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
        val texture = Texture(assetOf("map_bg.png"))
        add(PositionComponent())
        add(SpriteComponent(img = texture, depth = -100, scale = 0.5f))

    }
}

class BackgroundVignete : Entity() {
    init {
        val texture = Texture(assetOf("map_v.png"))
        add(PositionComponent())
        add(SpriteComponent(img = texture, depth = -99, scale = 0.5f))
    }
}

class BackgroundClouds : Entity() {
    init {
        val texture = Texture(assetOf("map_clouds.png"))
        add(PositionComponent())
        add(VelocityComponent())
        add(SpriteComponent(img = texture, depth = 888, scale = 0.5f))
    }
}

val lifeAnimation = Animation(listOf(
        Frame("life/llena.png", 500)
//        Frame("life/media.png", 500),
//        Frame("life/vacía.png", 500)
))

val bossAnimation = Animation(listOf(
        Frame("boss/1.png", 200),
        Frame("boss/2.png", 200),
        Frame("boss/3.png", 200),
        Frame("boss/4.png", 200)
))

val altarBoyDownAnimation = Animation(listOf(
    Frame("bomber/down/bomba1.png", 200),
    Frame("bomber/down/bomba2.png", 200),
    Frame("bomber/down/bomba3.png", 200)
))

val skeletonsAnimation = Animation(listOf(
    Frame("altarboy/skeleton/down/esqueleto1.png", 200),
    Frame("altarboy/skeleton/down/esqueleto2.png", 200),
    Frame("altarboy/skeleton/down/esqueleto3.png", 200)
))

val heroLeftAnimation = Animation(listOf(
        Frame("hero/leftAnimation/1.png", 200),
        Frame("hero/leftAnimation/2.png", 200),
        Frame("hero/leftAnimation/3.png", 200),
        Frame("hero/leftAnimation/4.png", 200),
        Frame("hero/leftAnimation/5.png", 200),
        Frame("hero/leftAnimation/6.png", 200)
))
val heroRightAnimation = Animation(listOf(
        Frame("hero/rightAnimation/1.png", 200),
        Frame("hero/rightAnimation/2.png", 200),
        Frame("hero/rightAnimation/3.png", 200),
        Frame("hero/rightAnimation/4.png", 200),
        Frame("hero/rightAnimation/5.png", 200),
        Frame("hero/rightAnimation/6.png", 200)
))
val heroDownAnimation = Animation(listOf(
        Frame("hero/downAnimation/1.png", 200),
        Frame("hero/downAnimation/2.png", 200),
        Frame("hero/downAnimation/3.png", 200),
        Frame("hero/downAnimation/4.png", 200)
))
val heroUpAnimation = Animation(listOf(
    Frame("hero/downAnimation/1.png", 200),
    Frame("hero/downAnimation/2.png", 200),
    Frame("hero/downAnimation/3.png", 200),
    Frame("hero/downAnimation/4.png", 200)
))

fun assetOf(asset: String) = Gdx.files.internal("assets/$asset")


class MenuBackground : Entity() {
    init {
        add(PositionComponent(x = 0f, y = 0f))
        add(SpriteComponent(img = Texture(assetOf("FirstAnimation/catedral_iluminada.png")), depth = 4, scale = 0.35f))
    }

}

class FirstBackground : Entity() {
    init {
        add(PositionComponent(x = 0f, y = 0f))
        add(SpriteComponent(img = Texture(assetOf("mapa 2/3.png")), depth = 4, scale = 0.35f))
    }

}

class ButtonPlay : Entity() {
    init {
        add(PositionComponent(x = 0f, y = 200f))
        add(SpriteComponent(img = Texture(assetOf("Buttons/play.png")), depth = 4, scale = 1f))
    }
}

class ButtonOptions : Entity() {
    init {
        add(PositionComponent(x = 0f, y = 100f))
        add(SpriteComponent(img = Texture(assetOf("Buttons/optionsButton.png")), depth = 4, scale = 1f))
    }
}

class ButtonExit : Entity() {
    init {
        add(PositionComponent(x = 0f, y = 0f))
        add(SpriteComponent(img = Texture(assetOf("Buttons/exitButton.png")), depth = 4, scale = 1f))
    }
}

class FirstBoss : Entity() {
    init {
        add(PositionComponent(x = -100f, y = -70f))
        add(SpriteComponent(img = Texture(assetOf("boss/boss.png")), depth = 4, scale = 0.2f))
    }
}

class FirstHero : Entity() {
    init {
        add(PositionComponent(x = 100f, y = +90f))
        add(SpriteComponent(img = Texture(assetOf("pj_final.png")), depth = 4, scale = 0.1f))
    }
}

class ClickableComponent(val rectangle: Rectangle) : Entity() {

}


class Boss : Entity() {
    init {
        val radius = 80f
        add(PositionComponent(x = 0f, y = 50f))
        add(VelocityComponent())
        add(CircleColliderComponent(radius, false))
        add(SpriteComponent(img = Texture(assetOf("boss/1.png")), depth = 4, scale = 0.2f))
        add(PrimitiveDrawingComponent(PrimitiveDrawingComponent.Shape.Circle(radius), Color.RED))
        add(BossLifeComponent())
        add(EnemyComponent(0f))
        add(BossStateComponent())
        add(DeadableComponent())
        add(AnimationComponent(bossAnimation))
    }

    class BossLifeComponent(var health: Int = 5) : Component

    class BossStateComponent(var stage: Stage = Stage.One) : Component {
        enum class Stage { One, Two, Three }
    }
}

class Hero : Entity() {

    init {
        val texture = Texture(assetOf("pj_final.png"))
        val radius = 30f
        add(PositionComponent(x = -462.47162f, y = 23.337389f))
        add(SpriteComponent(img = texture, depth = 3, scale = radius * 0.0028f))
        add(VelocityComponent())
        add(FrictionComponent(value = 0.1f))
        add(CircleColliderComponent(radius * 1f, canBeRepelled = false))
        add(PrimitiveDrawingComponent(PrimitiveDrawingComponent.Shape.Circle(radius), Color(0f, 0.5f, 0.0f, 1f)))
        add(SoulComponent())
        add(KeyboardAffectedComponent)
        add(MoveableByKeyboardComponent(enabled = true))
        add(LookAtComponent())
        add(ShotgunDelayComponent())
        add(LifeComponent())
        add(DeadableComponent())
        add(AnimationComponent(heroDownAnimation))
    }

    class ShotgunDelayComponent(var lastShootAcc: Float = 0f) : Component
}

class Skeleton(initialX: Float = 0f, initialY: Float = 0f) : Entity() {
    companion object {
        val texture by lazy { Texture(assetOf("altarboy/skeleton/down/esqueleto1.png")) }
    }

    init {
        val radius = 18f
        add(SpriteComponent(img = texture, depth = -5, scale = radius * 0.0030f))
        add(PositionComponent(x = initialX, y = initialY))
        add(VelocityComponent())
        add(CircleColliderComponent(radius * 0.75f))
        add(FrictionComponent(value = 0.13f))
        add(PrimitiveDrawingComponent(PrimitiveDrawingComponent.Shape.Circle(radius), Color.GOLD))
        add(SkeletonComponent())
        add(EnemyComponent(velocityMultiplier = 80f))
        add(MoveableByKeyboardComponent(enabled = false))
        add(DeadableComponent())
        add(AnimationComponent(skeletonsAnimation))

    }

    class SkeletonComponent : Component
}

class AltarBoy(initialX: Float = 0f, initialY: Float = 0f) : Entity() {
    companion object {
        val texture by lazy { Texture(assetOf("bomber/down/bomba1.png")) }
    }

    init {
        val radius = 24f
        add(SpriteComponent(img = texture, depth = -5, scale = radius * 0.0030f))
        add(PositionComponent(x = initialX, y = initialY))
        add(VelocityComponent())
        add(CircleColliderComponent(radius * 0.75f))
        add(FrictionComponent(value = 0.13f))
        add(PrimitiveDrawingComponent(PrimitiveDrawingComponent.Shape.Circle(radius), Color.FOREST))
        add(EnemyComponent(velocityMultiplier = 80f))
        add(MoveableByKeyboardComponent(enabled = false))
        add(DeadableComponent())
        add(AltarBoyComponent())
        add(AnimationComponent(altarBoyDownAnimation))
    }

    class AltarBoyComponent : Component
}

class Tomb : Entity() {
    init {
        val texture = Texture(assetOf("tomb.png"))
        add(PositionComponent(x = -50f, y = 200f))
        add(SpriteComponent(img = texture, depth = -11, scale = 0.25f))
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
        add(SpriteComponent(img = texture, depth = 1, scale = 0.33f))
        add(PositionComponent())
        add(PrimitiveDrawingComponent(PrimitiveDrawingComponent.Shape.Circle(radius), Color.DARK_GRAY))
        add(ShootComponent(vec2(1f, 0f)))
        add(VelocityComponent())
    }
}

class Life : Entity() {
    init {
        val texture = Texture(assetOf("vida.png"))
        add(LifeHeroComponent())
        add(PositionComponent(x = -475f, y = -250f))
        add(SpriteComponent(img = texture, depth = -1, scale = 0.09f, isUi = true))
        add(AnimationComponent(lifeAnimation))
    }
}

class SoulCursor : Entity() {
    companion object {
        val texture by lazy { Texture(assetOf("Bala.png")) }
    }

    init {
        val radius = 8f

        add(SpriteComponent(img = texture, depth = 500, scale = 0.38f, visible = false))
        add(PositionComponent())
        add(PrimitiveDrawingComponent(PrimitiveDrawingComponent.Shape.Circle(radius), Color.FIREBRICK))
        add(FrictionComponent(0.7f))
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