package com.hardcoredungeon.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.hardcoredungeon.HardcoreDungeon

/**
 * Title screen - main menu
 */
class TitleScene(game: HardcoreDungeon) : BaseScene(game) {

    private val batch = SpriteBatch()
    private val font = BitmapFont()

    init {
        font.color = Color.WHITE
        font.data.setScale(2f)
    }

    override fun show() {
        Gdx.app.log("TitleScene", "Showing title screen")
    }

    override fun render(delta: Float) {
        batch.begin()

        val centerX = Gdx.graphics.width / 2f
        val centerY = Gdx.graphics.height / 2f

        // Title
        val titleText = HardcoreDungeon.TITLE
        val titleLayout = font.draw(batch, titleText, 0f, 0f)
        font.draw(batch, titleText, centerX - titleLayout.width / 2, centerY + 100)

        // Instructions
        val startText = "Press ENTER to start"
        val startLayout = font.draw(batch, startText, 0f, 0f)
        font.draw(batch, startText, centerX - startLayout.width / 2, centerY)

        val quitText = "Press ESC to quit"
        val quitLayout = font.draw(batch, quitText, 0f, 0f)
        font.draw(batch, quitText, centerX - quitLayout.width / 2, centerY - 50)

        batch.end()

        // Input handling
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.screen = ClassSelectScene(game)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
        }
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
    }
}
