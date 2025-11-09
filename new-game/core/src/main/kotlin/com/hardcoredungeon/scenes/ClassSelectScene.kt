package com.hardcoredungeon.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.hardcoredungeon.HardcoreDungeon
import com.hardcoredungeon.actors.hero.HeroClass

/**
 * Class selection screen
 */
class ClassSelectScene(game: HardcoreDungeon) : BaseScene(game) {

    private val batch = SpriteBatch()
    private val font = BitmapFont()
    private var selectedClass = 0
    private val classes = HeroClass.values()

    init {
        font.color = Color.WHITE
        font.data.setScale(1.5f)
    }

    override fun render(delta: Float) {
        batch.begin()

        val centerX = Gdx.graphics.width / 2f
        var y = Gdx.graphics.height - 100f

        // Title
        font.draw(batch, "SELECT YOUR CLASS", centerX - 150, y)
        y -= 80

        // Class options
        classes.forEachIndexed { index, heroClass ->
            val prefix = if (index == selectedClass) "> " else "  "
            val color = if (index == selectedClass) Color.YELLOW else Color.WHITE
            font.color = color

            font.draw(batch, "$prefix${heroClass.title}", centerX - 100, y)
            font.color = Color.GRAY
            font.data.setScale(1f)
            font.draw(batch, heroClass.description, centerX - 100, y - 20)
            font.data.setScale(1.5f)

            y -= 80
        }

        font.color = Color.WHITE
        font.draw(batch, "UP/DOWN to select, ENTER to confirm", centerX - 200, 50f)
        font.draw(batch, "[S] Customize Skin  [ESC] Back", centerX - 150, 25f)

        batch.end()

        // Input
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedClass = (selectedClass - 1 + classes.size) % classes.size
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedClass = (selectedClass + 1) % classes.size
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.screen = GameScene(game, classes[selectedClass])
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            game.screen = SkinSelectionScene(game, classes[selectedClass])
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.screen = TitleScene(game)
        }
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
    }
}
