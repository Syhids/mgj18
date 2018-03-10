package io.syhids.mgj18

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import ktx.math.minus
import ktx.math.plus
import ktx.math.vec2

object KeyboardAffectedComponent : Component

class PositionComponent(
    var x: Float = 0f,
    var y: Float = 0f
) : Component {
    fun set(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun set(position: PositionComponent) {
        set(position.x, position.y)
    }

    operator fun minus(position: PositionComponent): Vector2 {
        return vec2(x, y) - vec2(position.x, position.y)
    }

    operator fun plusAssign(vector2: Vector2) {
        x += vector2.x
        y += vector2.y
    }

    operator fun plus(vec: Vector2) = vec2(x, y) + vec
}

class VelocityComponent : Component {
    var x: Float = 0f
    var y: Float = 0f

    val MAX_VELOCITY = 640f
    val POWER = 6000f
}

class FrictionComponent(
    var value: Float = 0f
) : Component

class EnemyComponent : Component

class PrimitiveDrawingComponent(
    val color: Color,
    val radius: Float
) : Component

class SpriteComponent(
    var img: Texture? = null,
    var scale: Float = 1f,
    var visible: Boolean = true,
    var depth: Int = 0
) : Component {
    val sprite: Sprite
        get() = Sprite(img)

    val midWidth: Int
        get() = width / 2
    val midHeight: Int
        get() = height / 2

    val width: Int
        get() = img?.let { it.width * scale }?.toInt() ?: 0
    val height: Int
        get() = img?.let { it.height * scale }?.toInt() ?: 0

    var alpha: Float = 1f
    var rotation: Float = 0f
}

class AnimationComponent(
    var speed: Float = 1f,
    val animation: Animation
) : Component {

    var state: State = State.Playing()
        set(value) {
            field = value
            if (value is State.PlayUntilFrame && currentAnimationIndex == value.frameIndex) {
                value.loopUntilNextFrame = true
            }
        }

    val isLastFrame: Boolean
        get() = animation.lastFrameIndex == currentAnimationIndex

    val isFirstFrame: Boolean
        get() = currentAnimationIndex == 0

    sealed class State {
        class Playing(val times: Int = -1) : State() {
            var currentTimes: Int = 0
        }

        object Paused : State()
        class PlayUntilFrame(val frameIndex: Int) : State()

        var loopUntilNextFrame: Boolean = false
    }

    private var accDelta: Float = 0f

    var currentAnimationIndex: Int = 0

    fun updateCurrentAnimation() {
        var deltaMs = accDelta * 1000 * speed

        while (deltaMs > animation.totalDurationMs) {
            deltaMs -= animation.totalDurationMs
        }

        animation.frames.forEachIndexed { frameIndex, frame ->
            deltaMs -= frame.duration

            if (deltaMs <= 0) {
                currentAnimationIndex = frameIndex

                val curState = state
                if (curState is State.PlayUntilFrame) {
                    if (frameIndex == curState.frameIndex && !curState.loopUntilNextFrame) {
                        state = State.Paused
                    } else {
                        val isNextFrame = curState.frameIndex == frameIndex - 1 || (frameIndex == 0 && curState.frameIndex == animation.lastFrameIndex)

                        if (isNextFrame && curState.loopUntilNextFrame)
                            curState.loopUntilNextFrame = false
                    }
                }

                return
            }
        }

        throw RuntimeException("Should not happen")
    }

    fun getCurrentAnimation(): Texture {
        return animation.frames[currentAnimationIndex].texture
    }

    fun step(deltaTime: Float) {
        if (state is State.Paused)
            return

        accDelta += deltaTime
    }

    fun reset() {
        accDelta = 0f
    }
}

data class Animation(val frames: List<Frame>) {
    constructor(vararg frames: Frame) : this(frames.toList())

    init {
        preload()
    }

    val totalDurationMs by lazy { frames.map { it.duration }.reduce { i, i2 -> i + i2 } }

    fun preload() {
        frames.forEach { it.texture }
    }

    val lastFrameIndex: Int
        get() = frames.size - 1
}

data class Frame(val imageName: String, val duration: Int) {
    val texture by lazy { Texture(imageName) }
}

class MovableComponent : Component {
    var shouldMoveRight: Boolean = false
    var shouldMoveLeft: Boolean = false
}

class ColliderComponent(
    var width: Float = 0f,
    var height: Float = 0f
) : Component {
    val midWidth: Float
        get() = width / 2

    val midHeight: Float
        get() = height / 2
}

class MenuComponent : Component {

}