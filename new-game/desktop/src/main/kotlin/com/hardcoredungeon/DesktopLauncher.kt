package com.hardcoredungeon

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

fun main() {
    val config = Lwjgl3ApplicationConfiguration().apply {
        setTitle("Hardcore Dungeon")
        setWindowedMode(800, 600)
        setForegroundFPS(60)
        useVsync(true)
    }

    Lwjgl3Application(HardcoreDungeon(), config)
}
