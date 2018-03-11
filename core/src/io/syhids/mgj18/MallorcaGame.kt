package io.syhids.mgj18

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import io.syhids.mgj18.system.*

@JvmField
val WORLD_WIDTH = 1280
@JvmField
val WORLD_HEIGHT: Int = (WORLD_WIDTH * 6 / 10).toInt()
val engine = Engine()

class MallorcaGame : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var shapeRenderer: ShapeRenderer
//    val particleEmitter = ParticleEmitter().also {
//        it.maxParticleCount = 200
//    }

    val particleEffect by lazy {
        ParticleEffect().also {
            it.load(assetOf("explo"), assetOf(""))
        }
    }

    lateinit var camera: OrthographicCamera
//    lateinit var font: BitmapFont
//    lateinit var bigFont: BitmapFont

    var GAME_SPEED = 2f
    val CAMERA_ZOOM = 1f

    override fun create() {
        batch = SpriteBatch()
        shapeRenderer = ShapeRenderer()
        shapeRenderer.setAutoShapeType(true)

        camera = OrthographicCamera(WORLD_WIDTH.toFloat() * CAMERA_ZOOM, WORLD_HEIGHT.toFloat() * CAMERA_ZOOM)

        engine.addEntity(Background())
        engine.addEntity(BackgroundVignete())
        engine.addEntity(BackgroundClouds())
        engine.addEntity(Boss())
        engine.addEntity(Hero())
        engine.addEntity(Life())
        engine.addEntity(Soul())
        engine.addEntity(SoulCursor())

        listOf(
            -140f to 250f,
            110f to 250f,
            -140f to -200f,
            110f to -200f
        ).forEach { (x, y) -> engine.addEntity(Tomb().also { it.position.set(x, y) }) }

        engine.addEntity(Tomb())
        engine.addEntity(Life())

        engine.addSystem(SpriteDrawingSystem(batch, camera))
        engine.addSystem(PrimitiveDrawingSystem(shapeRenderer, camera))
        engine.addSystem(MovementInputSystem())
        engine.addSystem(AccelerationSystem())
        engine.addSystem(FollowEnemySystem())
        engine.addSystem(CollisionAvoidSystem())
        engine.addSystem(SpawnEnemySystem())
        engine.addSystem(ShootingInputSystem())
        engine.addSystem(HeroLookAtInputSystem())
        engine.addSystem(CameraMovementSystem(camera))
        engine.addSystem(AnimationSystem())
        engine.addSystem(MovementSpriteSystem())
        engine.addSystem(SoulSystem())
        engine.addSystem(AltarBoyDeadSystem())
        engine.addSystem(SkeletonDeadSystem())
        engine.addSystem(BossStageSystem())

        particleEffect.start()

        val wallBounds = Rectangle(
            155f - (WORLD_WIDTH / 2f),
            180f - (WORLD_HEIGHT / 2f),
            WORLD_WIDTH - 340f,
            WORLD_HEIGHT - 300f
        )
        engine.addEntity(object : Entity() {
            init {
                add(PositionComponent(wallBounds.x, wallBounds.y))
                add(PrimitiveDrawingComponent(PrimitiveDrawingComponent.Shape.Rectangle(
                    wallBounds.width.toInt(),
                    wallBounds.height.toInt()
                ), Color.CYAN))
            }
        })
        engine.addSystem(KeepMovableEntitiesInsideTheWorldSystem(wallBounds))
        engine.addSystem(CleanEntitiesOutsideTheWorldSystem(Rectangle(
            -(WORLD_WIDTH / 2f + 300),
            -(WORLD_HEIGHT / 2f + 300),
            WORLD_WIDTH + 600f,
            WORLD_HEIGHT + 600f
        )))
        engine.addSystem(BulletCollisionSystem())
    }

    private fun generateFont(size: Int): BitmapFont {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("ALIN_KID.ttf"))
        val parameter = FreeTypeFontParameter()
        parameter.size = size
        parameter.color = Color.WHITE
        parameter.borderColor = Color.BLACK
        parameter.borderWidth = 3f
        val font = generator.generateFont(parameter)
        generator.dispose()

        return font
    }

    private var time: Float = 0f

    override fun render() {
        val realDt = Gdx.graphics.deltaTime
        val dt = realDt * GAME_SPEED

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            particleEffect.start()
        }

        engine.update(dt)
        particleEffect.update(realDt)

        batch.begin()
        particleEffect.draw(batch, realDt)
        batch.end()

        time += Gdx.graphics.deltaTime
    }

    override fun dispose() {
        batch.dispose()
    }
}