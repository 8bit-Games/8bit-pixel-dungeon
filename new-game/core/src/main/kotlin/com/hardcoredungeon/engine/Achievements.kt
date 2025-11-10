package com.hardcoredungeon.engine

/**
 * Achievement system for unlocking content and increasing engagement
 */
object Achievements {

    enum class Achievement(
        val id: String,
        val title: String,
        val description: String,
        val hidden: Boolean = false
    ) {
        // Combat achievements
        FIRST_KILL("first_kill", "First Blood", "Kill your first enemy"),
        KILL_10("kill_10", "Warrior", "Kill 10 enemies"),
        KILL_50("kill_50", "Veteran", "Kill 50 enemies"),
        KILL_100("kill_100", "Slayer", "Kill 100 enemies"),
        KILL_500("kill_500", "Legend", "Kill 500 enemies"),

        // Progression achievements
        DEPTH_5("depth_5", "Sewers Explorer", "Reach depth 5"),
        DEPTH_10("depth_10", "Prison Escapee", "Reach depth 10"),
        DEPTH_15("depth_15", "Cave Diver", "Reach depth 15"),
        DEPTH_20("depth_20", "City Conqueror", "Reach depth 20"),
        DEPTH_25("depth_25", "Hall Walker", "Reach depth 25"),

        // Item achievements
        COLLECT_100_GOLD("gold_100", "Prospector", "Collect 100 gold"),
        COLLECT_500_GOLD("gold_500", "Wealthy", "Collect 500 gold"),
        COLLECT_1000_GOLD("gold_1000", "Rich", "Collect 1000 gold"),

        // Survival achievements
        SURVIVE_100_TURNS("turns_100", "Survivor", "Survive 100 turns"),
        SURVIVE_500_TURNS("turns_500", "Endurance", "Survive 500 turns"),
        SURVIVE_1000_TURNS("turns_1000", "Immortal", "Survive 1000 turns"),

        // Class-specific achievements
        WIN_WARRIOR("win_warrior", "Warrior Master", "Win as Warrior"),
        WIN_MAGE("win_mage", "Archmage", "Win as Mage"),
        WIN_ROGUE("win_rogue", "Master Thief", "Win as Rogue"),
        WIN_RANGER("win_ranger", "Sharpshooter", "Win as Ranger"),

        // Challenge achievements
        NO_DAMAGE_5("no_dmg_5", "Untouchable", "Reach depth 5 without taking damage", hidden = true),
        FULL_INVENTORY("full_inv", "Pack Rat", "Fill your inventory"),
        EAT_100_FOOD("food_100", "Glutton", "Eat 100 food items"),

        // Death achievements
        STARVE("starve", "Hunger Pains", "Die from starvation"),
        DIE_10("death_10", "Persistent", "Die 10 times"),
        DIE_50("death_50", "Never Give Up", "Die 50 times"),

        // Rare achievements
        KILL_BOSS("boss", "Boss Slayer", "Defeat a boss", hidden = true),
        WIN_GAME("victory", "Champion", "Win the game", hidden = true),
        PERFECT_RUN("perfect", "Flawless Victory", "Win without dying", hidden = true);
    }

    private val unlockedAchievements = mutableSetOf<String>()
    private val progress = mutableMapOf<String, Int>()

    fun unlock(achievement: Achievement) {
        if (!isUnlocked(achievement)) {
            unlockedAchievements.add(achievement.id)
            // TODO: Show notification
            // TODO: Save to disk
        }
    }

    fun isUnlocked(achievement: Achievement): Boolean {
        return unlockedAchievements.contains(achievement.id)
    }

    fun getUnlockedCount(): Int {
        return unlockedAchievements.size
    }

    fun getTotalCount(): Int {
        return Achievement.values().size
    }

    fun getProgress(key: String): Int {
        return progress.getOrDefault(key, 0)
    }

    fun incrementProgress(key: String, amount: Int = 1) {
        progress[key] = getProgress(key) + amount
        checkAchievements(key)
    }

    private fun checkAchievements(key: String) {
        when (key) {
            "kills" -> {
                val kills = getProgress("kills")
                if (kills >= 1) unlock(Achievement.FIRST_KILL)
                if (kills >= 10) unlock(Achievement.KILL_10)
                if (kills >= 50) unlock(Achievement.KILL_50)
                if (kills >= 100) unlock(Achievement.KILL_100)
                if (kills >= 500) unlock(Achievement.KILL_500)
            }
            "gold" -> {
                val gold = getProgress("gold")
                if (gold >= 100) unlock(Achievement.COLLECT_100_GOLD)
                if (gold >= 500) unlock(Achievement.COLLECT_500_GOLD)
                if (gold >= 1000) unlock(Achievement.COLLECT_1000_GOLD)
            }
            "turns" -> {
                val turns = getProgress("turns")
                if (turns >= 100) unlock(Achievement.SURVIVE_100_TURNS)
                if (turns >= 500) unlock(Achievement.SURVIVE_500_TURNS)
                if (turns >= 1000) unlock(Achievement.SURVIVE_1000_TURNS)
            }
            "deaths" -> {
                val deaths = getProgress("deaths")
                if (deaths >= 10) unlock(Achievement.DIE_10)
                if (deaths >= 50) unlock(Achievement.DIE_50)
            }
        }
    }

    fun checkDepthAchievement(depth: Int) {
        when {
            depth >= 5 -> unlock(Achievement.DEPTH_5)
            depth >= 10 -> unlock(Achievement.DEPTH_10)
            depth >= 15 -> unlock(Achievement.DEPTH_15)
            depth >= 20 -> unlock(Achievement.DEPTH_20)
            depth >= 25 -> unlock(Achievement.DEPTH_25)
        }
    }

    fun getAllAchievements(): List<Achievement> {
        return Achievement.values().toList()
    }

    fun getVisibleAchievements(): List<Achievement> {
        return Achievement.values().filter { !it.hidden || isUnlocked(it) }
    }
}
