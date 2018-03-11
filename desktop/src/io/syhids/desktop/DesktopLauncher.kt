package io.syhids.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import io.syhids.mgj18.MallorcaGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.width = 1280
        config.height = 1280 * 6 / 10
        config.fullscreen = false
        LwjglApplication(MallorcaGame(), config)
    }
}