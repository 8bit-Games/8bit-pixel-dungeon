package com.hardcoredungeon.engine

import com.hardcoredungeon.actors.hero.HeroClass

/**
 * Game statistics tracking for player engagement
 */
object GameStats {

    data class Stats(
        // Combat stats
        var totalKills: Int = 0,
        var totalDeaths: Int = 0,
        var bossKills: Int = 0,

        // Progression stats
        var deepestDepth: Int = 0,
        var totalGoldCollected: Int = 0,
        var totalItemsCollected: Int = 0,
        var totalFoodEaten: Int = 0,

        // Time stats
        var totalPlayTime: Long = 0,  // milliseconds
        var totalTurns: Int = 0,
        var longestRun: Int = 0,  // turns

        // Class stats
        var warriorKills: Int = 0,
        var mageKills: Int = 0,
        var rogueKills: Int = 0,
        var rangerKills: Int = 0,

        // Win tracking
        var totalWins: Int = 0,
        var warriorWins: Int = 0,
        var mageWins: Int = 0,
        var rogueWins: Int = 0,
        var rangerWins: Int = 0
    )

    private var stats = Stats()
    private var currentRunStartTime: Long = 0
    private var currentRunTurns: Int = 0

    fun getStats(): Stats = stats

    fun startRun() {
        currentRunStartTime = System.currentTimeMillis()
        currentRunTurns = 0
    }

    fun endRun(depth: Int, heroClass: HeroClass, won: Boolean = false) {
        val runTime = System.currentTimeMillis() - currentRunStartTime
        stats.totalPlayTime += runTime
        stats.totalDeaths++

        if (currentRunTurns > stats.longestRun) {
            stats.longestRun = currentRunTurns
        }

        if (depth > stats.deepestDepth) {
            stats.deepestDepth = depth
        }

        if (won) {
            stats.totalWins++
            when (heroClass) {
                HeroClass.WARRIOR -> stats.warriorWins++
                HeroClass.MAGE -> stats.mageWins++
                HeroClass.ROGUE -> stats.rogueWins++
                HeroClass.RANGER -> stats.rangerWins++
            }
        }

        Achievements.incrementProgress("deaths", 1)
        Achievements.checkDepthAchievement(depth)

        save()
    }

    fun recordKill(heroClass: HeroClass, isBoss: Boolean = false) {
        stats.totalKills++

        when (heroClass) {
            HeroClass.WARRIOR -> stats.warriorKills++
            HeroClass.MAGE -> stats.mageKills++
            HeroClass.ROGUE -> stats.rogueKills++
            HeroClass.RANGER -> stats.rangerKills++
        }

        if (isBoss) {
            stats.bossKills++
            Achievements.unlock(Achievements.Achievement.KILL_BOSS)
        }

        Achievements.incrementProgress("kills", 1)
    }

    fun recordGold(amount: Int) {
        stats.totalGoldCollected += amount
        Achievements.incrementProgress("gold", amount)
    }

    fun recordItemCollected() {
        stats.totalItemsCollected++
    }

    fun recordFoodEaten() {
        stats.totalFoodEaten++
        Achievements.incrementProgress("food_eaten", 1)
        if (stats.totalFoodEaten >= 100) {
            Achievements.unlock(Achievements.Achievement.EAT_100_FOOD)
        }
    }

    fun recordTurn() {
        currentRunTurns++
        stats.totalTurns++
        Achievements.incrementProgress("turns", 1)
    }

    fun getWinRate(heroClass: HeroClass): Float {
        val wins = when (heroClass) {
            HeroClass.WARRIOR -> stats.warriorWins
            HeroClass.MAGE -> stats.mageWins
            HeroClass.ROGUE -> stats.rogueWins
            HeroClass.RANGER -> stats.rangerWins
        }
        val total = stats.totalDeaths
        return if (total > 0) wins.toFloat() / total else 0f
    }

    fun getPlayTimeHours(): Float {
        return stats.totalPlayTime / (1000f * 60f * 60f)
    }

    private fun save() {
        // TODO: Persist to disk
    }

    fun load() {
        // TODO: Load from disk
    }
}
