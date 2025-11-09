package com.hardcoredungeon.engine

import java.time.LocalDate
import kotlin.random.Random

/**
 * Daily Challenge system - same seed for all players each day
 * Drives player engagement and social sharing
 */
object DailyChallenge {

    data class DailySeed(
        val date: LocalDate,
        val seed: Long,
        val depth: Int = 0,
        val score: Int = 0
    )

    private var currentChallenge: DailySeed? = null

    fun getTodaysSeed(): Long {
        val today = LocalDate.now()

        if (currentChallenge?.date != today) {
            // Generate new seed based on date
            val seed = today.toEpochDay()
            currentChallenge = DailySeed(today, seed)
        }

        return currentChallenge!!.seed
    }

    fun getTodaysDate(): String {
        return LocalDate.now().toString()
    }

    fun isToday(date: LocalDate): Boolean {
        return date == LocalDate.now()
    }

    fun recordScore(depth: Int, score: Int) {
        currentChallenge?.let {
            if (it.date == LocalDate.now()) {
                currentChallenge = it.copy(depth = depth, score = score)
                saveLocalScore(it.copy(depth = depth, score = score))
            }
        }
    }

    private fun saveLocalScore(challenge: DailySeed) {
        // Save to local storage for leaderboard
        val scores = getLocalScores().toMutableList()
        scores.removeAll { it.date == challenge.date }
        scores.add(challenge)
        scores.sortByDescending { it.score }

        // Keep only last 30 days
        val cutoff = LocalDate.now().minusDays(30)
        scores.removeAll { it.date.isBefore(cutoff) }

        // TODO: Persist to disk
    }

    fun getLocalScores(): List<DailySeed> {
        // TODO: Load from disk
        return emptyList()
    }

    fun getTodaysBestScore(): Int {
        return currentChallenge?.score ?: 0
    }
}
