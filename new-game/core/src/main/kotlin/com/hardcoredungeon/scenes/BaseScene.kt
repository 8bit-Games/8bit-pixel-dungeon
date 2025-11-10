package com.hardcoredungeon.scenes

import com.badlogic.gdx.Screen
import com.hardcoredungeon.HardcoreDungeon

/**
 * Base class for all game scenes/screens
 */
abstract class BaseScene(protected val game: HardcoreDungeon) : Screen {

    override fun show() {}

    override fun render(delta: Float) {}

    override fun resize(width: Int, height: Int) {}

    override fun pause() {}

    override fun resume() {}

    override fun hide() {}

    override fun dispose() {}
}
