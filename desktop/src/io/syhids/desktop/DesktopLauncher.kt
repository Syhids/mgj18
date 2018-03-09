package io.syhids.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import io.syhids.MyGdxGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.width = 800
        config.height = 600
        config.fullscreen = false
        LwjglApplication(MyGdxGame(), config)
    }
}