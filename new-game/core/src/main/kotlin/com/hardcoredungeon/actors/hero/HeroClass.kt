package com.hardcoredungeon.actors.hero

/**
 * Player character classes with unique abilities and starting stats
 */
enum class HeroClass(
    val title: String,
    val description: String,
    val baseHP: Int,
    val baseStr: Int,
    val baseAttack: Int,
    val baseDefense: Int
) {
    WARRIOR(
        title = "Warrior",
        description = "Strong melee fighter with high HP and defense",
        baseHP = 20,
        baseStr = 12,
        baseAttack = 3,
        baseDefense = 2
    ),

    MAGE(
        title = "Mage",
        description = "Master of magic with powerful spells",
        baseHP = 12,
        baseStr = 8,
        baseAttack = 2,
        baseDefense = 0
    ),

    ROGUE(
        title = "Rogue",
        description = "Sneaky assassin with critical strikes",
        baseHP = 15,
        baseStr = 10,
        baseAttack = 4,
        baseDefense = 1
    ),

    RANGER(
        title = "Ranger",
        description = "Ranged specialist with keen senses",
        baseHP = 16,
        baseStr = 10,
        baseAttack = 3,
        baseDefense = 1
    )
}
