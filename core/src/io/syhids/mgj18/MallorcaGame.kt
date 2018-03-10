package io.syhids.mgj18

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.syhids.mgj18.system.*

@JvmField
val WORLD_WIDTH = 1280
@JvmField
val WORLD_HEIGHT: Int = (WORLD_WIDTH * 6 / 10).toInt()
val engine = Engine()

class MallorcaGame : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var shapeRenderer: ShapeRenderer

    lateinit var camera: OrthographicCamera
//    lateinit var font: BitmapFont
//    lateinit var bigFont: BitmapFont

    var GAME_SPEED = 2f


    override fun create() {
        batch = SpriteBatch()
        shapeRenderer = ShapeRenderer()
        shapeRenderer.setAutoShapeType(true)

        camera = OrthographicCamera(WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat())

        engine.addEntity(Background())
        engine.addEntity(Boss())
        engine.addEntity(Hero())
        engine.addEntity(Enemy(initialX = 200f))
        engine.addEntity(Tomb())

        engine.addSystem(SpriteDrawingSystem(batch, camera))
        engine.addSystem(PrimitiveDrawingSystem(shapeRenderer, camera))
        engine.addSystem(InputSystem())
        engine.addSystem(AccelerationSystem())
        engine.addSystem(FollowEnemySystem())
        engine.addSystem(CollisionAvoidSystem())
        engine.addSystem(SpawnEnemySystem())
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
        val dt = Gdx.graphics.deltaTime * GAME_SPEED

        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        engine.update(dt)

        time += Gdx.graphics.deltaTime
    }

    override fun dispose() {
        batch.dispose()
    }
}