package com.hardcoredungeon.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.hardcoredungeon.HardcoreDungeon
import com.hardcoredungeon.actors.hero.HeroClass
import com.hardcoredungeon.engine.DailyChallenge

/**
 * Daily challenge selection screen
 */
class DailyChallengeScene(game: HardcoreDungeon) : BaseScene(game) {

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
        font.color = Color.YELLOW
        font.data.setScale(2f)
        font.draw(batch, "DAILY CHALLENGE", centerX - 150, y)
        font.data.setScale(1.2f)
        y -= 60f

        // Today's date
        font.color = Color.CYAN
        font.draw(batch, "Today: ${DailyChallenge.getTodaysDate()}", centerX - 100, y)
        y -= 40f

        // Info
        font.color = Color.WHITE
        font.draw(batch, "Everyone plays the same dungeon today!", centerX - 200, y)
        y -= 25f
        font.draw(batch, "Compete for the highest score!", centerX - 150, y)
        y -= 60f

        // Best score
        val bestScore = DailyChallenge.getTodaysBestScore()
        if (bestScore > 0) {
            font.color = Color.GREEN
            font.draw(batch, "Your best today: $bestScore", centerX - 120, y)
            y -= 40f
        }

        // Class selection
        font.color = Color.WHITE
        font.draw(batch, "SELECT YOUR CLASS", centerX - 120, y)
        y -= 40f

        classes.forEachIndexed { index, heroClass ->
            val prefix = if (index == selectedClass) "> " else "  "
            font.color = if (index == selectedClass) Color.YELLOW else Color.WHITE

            font.draw(batch, "$prefix${heroClass.title}", centerX - 100, y)
            font.color = Color.GRAY
            font.data.setScale(1f)
            font.draw(batch, heroClass.description, centerX - 100, y - 20)
            font.data.setScale(1.2f)

            y -= 60
        }

        // Controls
        font.color = Color.GRAY
        font.data.setScale(1f)
        font.draw(batch, "UP/DOWN to select, ENTER to start, ESC to back", centerX - 200, 50f)

        batch.end()

        handleInput()
    }

    private fun handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedClass = (selectedClass - 1 + classes.size) % classes.size
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedClass = (selectedClass + 1) % classes.size
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            // Start daily challenge with today's seed
            val seed = DailyChallenge.getTodaysSeed()
            game.screen = GameScene(game, classes[selectedClass], isDaily = true, seed = seed)
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
