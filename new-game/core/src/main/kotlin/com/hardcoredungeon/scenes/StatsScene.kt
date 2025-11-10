package com.hardcoredungeon.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.hardcoredungeon.HardcoreDungeon
import com.hardcoredungeon.engine.Achievements
import com.hardcoredungeon.engine.GameStats

/**
 * Stats and achievements screen
 */
class StatsScene(game: HardcoreDungeon) : BaseScene(game) {

    private val batch = SpriteBatch()
    private val font = BitmapFont()
    private var selectedTab = Tab.STATS

    enum class Tab { STATS, ACHIEVEMENTS }

    init {
        font.color = Color.WHITE
        font.data.setScale(1.2f)
    }

    override fun render(delta: Float) {
        batch.begin()

        val screenWidth = Gdx.graphics.width.toFloat()
        val screenHeight = Gdx.graphics.height.toFloat()
        var y = screenHeight - 40f

        // Title
        font.color = Color.YELLOW
        font.data.setScale(2f)
        font.draw(batch, "STATISTICS", screenWidth / 2 - 100, y)
        font.data.setScale(1.2f)
        y -= 60f

        // Tab selection
        font.color = if (selectedTab == Tab.STATS) Color.YELLOW else Color.GRAY
        font.draw(batch, "[1] Stats", 40f, y)

        font.color = if (selectedTab == Tab.ACHIEVEMENTS) Color.YELLOW else Color.GRAY
        font.draw(batch, "[2] Achievements (${Achievements.getUnlockedCount()}/${Achievements.getTotalCount()})", 200f, y)
        y -= 40f

        // Content
        when (selectedTab) {
            Tab.STATS -> renderStats(y)
            Tab.ACHIEVEMENTS -> renderAchievements(y)
        }

        // Footer
        font.color = Color.GRAY
        font.data.setScale(1f)
        font.draw(batch, "[1/2] Switch Tab  [ESC] Back", 40f, 20f)

        batch.end()

        handleInput()
    }

    private fun renderStats(startY: Float) {
        var y = startY
        val stats = GameStats.getStats()

        font.color = Color.CYAN
        font.draw(batch, "=== COMBAT ===", 40f, y)
        y -= 30f

        font.color = Color.WHITE
        font.draw(batch, "Total Kills: ${stats.totalKills}", 60f, y)
        y -= 25f
        font.draw(batch, "Boss Kills: ${stats.bossKills}", 60f, y)
        y -= 25f
        font.draw(batch, "Total Deaths: ${stats.totalDeaths}", 60f, y)
        y -= 40f

        font.color = Color.CYAN
        font.draw(batch, "=== PROGRESSION ===", 40f, y)
        y -= 30f

        font.color = Color.WHITE
        font.draw(batch, "Deepest Depth: ${stats.deepestDepth}", 60f, y)
        y -= 25f
        font.draw(batch, "Total Gold: ${stats.totalGoldCollected}", 60f, y)
        y -= 25f
        font.draw(batch, "Items Collected: ${stats.totalItemsCollected}", 60f, y)
        y -= 25f
        font.draw(batch, "Food Eaten: ${stats.totalFoodEaten}", 60f, y)
        y -= 40f

        font.color = Color.CYAN
        font.draw(batch, "=== TIME ===", 40f, y)
        y -= 30f

        font.color = Color.WHITE
        font.draw(batch, "Play Time: %.1f hours".format(GameStats.getPlayTimeHours()), 60f, y)
        y -= 25f
        font.draw(batch, "Total Turns: ${stats.totalTurns}", 60f, y)
        y -= 25f
        font.draw(batch, "Longest Run: ${stats.longestRun} turns", 60f, y)
        y -= 40f

        font.color = Color.CYAN
        font.draw(batch, "=== VICTORIES ===", 40f, y)
        y -= 30f

        font.color = Color.WHITE
        font.draw(batch, "Total Wins: ${stats.totalWins}", 60f, y)
        y -= 25f
        font.draw(batch, "Warrior Wins: ${stats.warriorWins}", 60f, y)
        y -= 25f
        font.draw(batch, "Mage Wins: ${stats.mageWins}", 60f, y)
        y -= 25f
        font.draw(batch, "Rogue Wins: ${stats.rogueWins}", 60f, y)
        y -= 25f
        font.draw(batch, "Ranger Wins: ${stats.rangerWins}", 60f, y)
    }

    private fun renderAchievements(startY: Float) {
        var y = startY

        val achievements = Achievements.getVisibleAchievements()
        val unlocked = achievements.filter { Achievements.isUnlocked(it) }
        val locked = achievements.filter { !Achievements.isUnlocked(it) }

        // Unlocked achievements
        if (unlocked.isNotEmpty()) {
            font.color = Color.GREEN
            font.draw(batch, "=== UNLOCKED ===", 40f, y)
            y -= 30f

            for (achievement in unlocked.take(10)) {
                font.color = Color.YELLOW
                font.draw(batch, "✓ ${achievement.title}", 60f, y)
                y -= 20f
                font.color = Color.GRAY
                font.data.setScale(0.9f)
                font.draw(batch, achievement.description, 80f, y)
                font.data.setScale(1.2f)
                y -= 25f
            }
        }

        // Locked achievements
        if (locked.isNotEmpty()) {
            y -= 10f
            font.color = Color.GRAY
            font.draw(batch, "=== LOCKED ===", 40f, y)
            y -= 30f

            for (achievement in locked.take(5)) {
                font.color = Color.DARK_GRAY
                font.draw(batch, "? ${achievement.title}", 60f, y)
                y -= 20f
                font.data.setScale(0.9f)
                font.draw(batch, achievement.description, 80f, y)
                font.data.setScale(1.2f)
                y -= 25f
            }
        }
    }

    private fun handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            selectedTab = Tab.STATS
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            selectedTab = Tab.ACHIEVEMENTS
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
