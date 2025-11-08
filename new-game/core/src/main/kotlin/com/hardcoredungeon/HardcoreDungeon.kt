package com.hardcoredungeon

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.hardcoredungeon.scenes.TitleScene

/**
 * Main game class for Hardcore Dungeon
 * A brutal roguelike with permadeath and hardcore survival mechanics
 */
class HardcoreDungeon : Game() {

    companion object {
        const val TITLE = "Hardcore Dungeon"
        const val VERSION = "1.0.0"
        const val TILE_SIZE = 16
        const val VIEWPORT_WIDTH = 20
        const val VIEWPORT_HEIGHT = 20
    }

    override fun create() {
        Gdx.app.log("HardcoreDungeon", "Starting $TITLE v$VERSION")
        setScreen(TitleScene(this))
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        super.render()
    }

    override fun dispose() {
        screen?.dispose()
    }
}
