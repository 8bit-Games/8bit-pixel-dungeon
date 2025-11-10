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
    private var selectedOption = 0
    private val options = listOf("Start Game", "Daily Challenge", "Stats & Achievements", "Quit")

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
        font.color = Color.YELLOW
        font.data.setScale(3f)
        val titleText = HardcoreDungeon.TITLE
        val titleLayout = font.draw(batch, titleText, 0f, 0f)
        font.draw(batch, titleText, centerX - titleLayout.width / 2, centerY + 150)

        // Version
        font.color = Color.GRAY
        font.data.setScale(1f)
        font.draw(batch, "v${HardcoreDungeon.VERSION}", centerX + 100, centerY + 120)

        // Menu options
        font.data.setScale(1.5f)
        var y = centerY + 20

        options.forEachIndexed { index, option ->
            val prefix = if (index == selectedOption) "> " else "  "
            font.color = if (index == selectedOption) Color.YELLOW else Color.WHITE

            val text = "$prefix$option"
            val layout = font.draw(batch, text, 0f, 0f)
            font.draw(batch, text, centerX - layout.width / 2, y)

            y -= 50
        }

        // Controls
        font.color = Color.GRAY
        font.data.setScale(1f)
        font.draw(batch, "UP/DOWN to select, ENTER to confirm", centerX - 150, 50f)

        batch.end()

        // Input handling
        handleInput()
    }

    private fun handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedOption = (selectedOption - 1 + options.size) % options.size
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption + 1) % options.size
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            when (selectedOption) {
                0 -> game.screen = ClassSelectScene(game)
                1 -> game.screen = DailyChallengeScene(game)
                2 -> game.screen = StatsScene(game)
                3 -> Gdx.app.exit()
            }
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
