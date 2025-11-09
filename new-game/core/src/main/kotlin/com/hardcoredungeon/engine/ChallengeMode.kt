package com.hardcoredungeon.engine

/**
 * Challenge modes for hardcore players
 * Increases replayability and mastery
 */
enum class ChallengeMode(
    val displayName: String,
    val description: String,
    val scoreMultiplier: Float
) {
    NORMAL(
        "Normal",
        "Standard roguelike experience",
        1.0f
    ),

    DAILY_CHALLENGE(
        "Daily Challenge",
        "Same seed for all players today - compete globally!",
        1.5f
    ),

    IRON_MAN(
        "Iron Man",
        "No food spawns - manage hunger carefully!",
        2.0f
    ),

    SPEED_RUN(
        "Speed Run",
        "Race against time - bonus score for speed",
        1.8f
    ),

    PACIFIST(
        "Pacifist",
        "Win without killing (except bosses)",
        3.0f
    ),

    ENDLESS(
        "Endless Mode",
        "See how deep you can go - infinite depths",
        1.2f
    ),

    HARDCORE(
        "Hardcore",
        "All challenges enabled - for masochists only!",
        5.0f
    );

    fun isNoFood(): Boolean = this == IRON_MAN || this == HARDCORE
    fun isSpeedRun(): Boolean = this == SPEED_RUN || this == HARDCORE
    fun isPacifist(): Boolean = this == PACIFIST || this == HARDCORE
    fun isDaily(): Boolean = this == DAILY_CHALLENGE
    fun isEndless(): Boolean = this == ENDLESS || this == HARDCORE
}

/**
 * Tracks active challenge modifiers
 */
object Challenge {
    var activeMode: ChallengeMode = ChallengeMode.NORMAL
    var startTime: Long = 0
    private var killCount: Int = 0

    fun start(mode: ChallengeMode) {
        activeMode = mode
        startTime = System.currentTimeMillis()
        killCount = 0
    }

    fun recordKill() {
        killCount++
    }

    fun getKills(): Int = killCount

    fun getElapsedTime(): Long {
        return System.currentTimeMillis() - startTime
    }

    fun calculateScore(depth: Int, gold: Int): Int {
        var score = depth * 100 + gold

        // Speed run bonus
        if (activeMode.isSpeedRun()) {
            val minutes = getElapsedTime() / (1000 * 60)
            val speedBonus = maxOf(0, 1000 - minutes.toInt() * 10)
            score += speedBonus
        }

        // Pacifist bonus
        if (activeMode.isPacifist() && killCount < 10) {
            score += 2000
        }

        return (score * activeMode.scoreMultiplier).toInt()
    }

    fun reset() {
        activeMode = ChallengeMode.NORMAL
        killCount = 0
    }
}
