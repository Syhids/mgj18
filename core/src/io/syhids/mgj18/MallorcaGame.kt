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
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool
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

val gameEngine = Engine()
val menuEngine = Engine()
val FirstSceneEngine = Engine()

var currentGameState: GameState = GameState.MenuMode

enum class GameState {
    MenuMode,
    FirstSceneEngine,
    GameMode
}

val explosionEffect: ParticleEffectPool.PooledEffect
    get() = explosionEffectPool.obtain()

val explosionEffectPool = ParticleEffectPool(ParticleEffect().also {
    it.load(assetOf("explo"), assetOf(""))
}, 5, 10)

class MallorcaGame : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var shapeRenderer: ShapeRenderer

    lateinit var camera: OrthographicCamera
    lateinit var uiCamera: OrthographicCamera
//    lateinit var font: BitmapFont
//    lateinit var bigFont: BitmapFont

    var GAME_SPEED = 2f
    val CAMERA_ZOOM = 1f

    override fun create() {
        batch = SpriteBatch()
        shapeRenderer = ShapeRenderer()
        shapeRenderer.setAutoShapeType(true)

        camera = OrthographicCamera(WORLD_WIDTH.toFloat() * CAMERA_ZOOM, WORLD_HEIGHT.toFloat() * CAMERA_ZOOM)
        uiCamera = OrthographicCamera(WORLD_WIDTH.toFloat() * CAMERA_ZOOM, WORLD_HEIGHT.toFloat() * CAMERA_ZOOM)


        initGameEngine()
        initFirstScebeEngine()
        initMenuEngine()
    }

    private fun initMenuEngine() {
        menuEngine.addEntity(MenuBackground())
        menuEngine.addEntity(ButtonPlay())
        menuEngine.addEntity(ButtonOptions())
        menuEngine.addEntity(ButtonExit())


        menuEngine.addSystem(SpriteDrawingSystem(batch, camera))
        menuEngine.addSystem(SpriteUiDrawingSystem(batch, uiCamera))
        menuEngine.addSystem(PrimitiveDrawingSystem(shapeRenderer, camera))
        menuEngine.addSystem(MovementInputSystem())
        menuEngine.addSystem(AccelerationSystem())
        menuEngine.addSystem(AnimationSystem())
        menuEngine.addSystem(MovementSpriteSystem())
    }

    private fun initFirstScebeEngine() {
        FirstSceneEngine.addEntity(MenuBackground())


        FirstSceneEngine.addSystem(SpriteDrawingSystem(batch, camera))
        FirstSceneEngine.addSystem(SpriteUiDrawingSystem(batch, uiCamera))
        FirstSceneEngine.addSystem(PrimitiveDrawingSystem(shapeRenderer, camera))
        FirstSceneEngine.addSystem(AnimationSystem())
        FirstSceneEngine.addSystem(MovementSpriteSystem())
    }

    private fun initGameEngine() {
        gameEngine.addEntity(Background())
        gameEngine.addEntity(BackgroundVignete())
        gameEngine.addEntity(BackgroundClouds())
        gameEngine.addEntity(Boss())
        gameEngine.addEntity(Hero())
        gameEngine.addEntity(Life())
        gameEngine.addEntity(SoulCursor())

        listOf(
                -140f to 250f,
                110f to 250f,
                -140f to -200f,
                110f to -200f
        ).forEach { (x, y) -> gameEngine.addEntity(Tomb().also { it.position.set(x, y) }) }

        gameEngine.addEntity(Tomb())
        gameEngine.addEntity(Life())

        gameEngine.addSystem(SpriteDrawingSystem(batch, camera))
        gameEngine.addSystem(SpriteUiDrawingSystem(batch, uiCamera))
        gameEngine.addSystem(PrimitiveDrawingSystem(shapeRenderer, camera))
        gameEngine.addSystem(MovementInputSystem())
        gameEngine.addSystem(AccelerationSystem())
        gameEngine.addSystem(FollowEnemySystem())
        gameEngine.addSystem(CollisionAvoidSystem())
        gameEngine.addSystem(SpawnEnemySystem())
        gameEngine.addSystem(ShootingInputSystem())
        gameEngine.addSystem(HeroLookAtInputSystem())
        gameEngine.addSystem(CameraMovementSystem(camera))
        gameEngine.addSystem(AnimationSystem())
        gameEngine.addSystem(MovementSpriteSystem())
        gameEngine.addSystem(SoulSystem())
        gameEngine.addSystem(AltarBoyDeadSystem())
        gameEngine.addSystem(SkeletonDeadSystem())
        gameEngine.addSystem(BossStageSystem())
        gameEngine.addSystem(ParticleDrawingSystem(batch, camera))
        gameEngine.addSystem(RemoveAfterMsSystem())

        val wallBounds = Rectangle(
                155f - (WORLD_WIDTH / 2f),
                180f - (WORLD_HEIGHT / 2f),
                WORLD_WIDTH - 340f,
                WORLD_HEIGHT - 300f
        )
        gameEngine.addEntity(object : Entity() {
            init {
                add(PositionComponent(wallBounds.x, wallBounds.y))
                add(PrimitiveDrawingComponent(PrimitiveDrawingComponent.Shape.Rectangle(
                        wallBounds.width.toInt(),
                        wallBounds.height.toInt()
                ), Color.CYAN))
            }
        })
        gameEngine.addSystem(KeepMovableEntitiesInsideTheWorldSystem(wallBounds))
        gameEngine.addSystem(CleanEntitiesOutsideTheWorldSystem(Rectangle(
                -(WORLD_WIDTH / 2f + 300),
                -(WORLD_HEIGHT / 2f + 300),
                WORLD_WIDTH + 600f,
                WORLD_HEIGHT + 600f
        )))
        gameEngine.addSystem(BulletCollisionSystem())
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            currentGameState = GameState.GameMode
        }

        val currentEngine = when (currentGameState) {
            GameState.MenuMode -> menuEngine
            GameState.GameMode -> gameEngine
            GameState.FirstSceneEngine -> FirstSceneEngine
        }

        currentEngine.update(dt)

        time += Gdx.graphics.deltaTime
    }

    override fun dispose() {
        batch.dispose()
    }
}