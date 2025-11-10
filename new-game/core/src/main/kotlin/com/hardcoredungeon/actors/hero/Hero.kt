package com.hardcoredungeon.actors.hero

import com.hardcoredungeon.actors.Actor
import com.hardcoredungeon.actors.buffs.Buff
import com.hardcoredungeon.levels.Level

/**
 * The player character
 */
class Hero(val heroClass: HeroClass) : Actor() {

    var strength: Int = heroClass.baseStr
    var attack: Int = heroClass.baseAttack
    var defense: Int = heroClass.baseDefense

    var gold: Int = 0
    var hunger: Int = 1000  // Starts full, decreases over time
    var experience: Int = 0
    var level: Int = 1

    val inventory: Inventory = Inventory()
    val buffs: MutableList<Buff> = mutableListOf()

    init {
        maxHP = heroClass.baseHP
        hp = maxHP
    }

    fun move(dx: Int, dy: Int, level: Level): Boolean {
        val newPos = pos.offset(dx, dy)

        if (!level.isValid(newPos.x, newPos.y)) {
            return false
        }

        val tile = level.getTile(newPos.x, newPos.y)
        if (tile.solid) {
            return false
        }

        pos = newPos
        return true
    }

    fun gainExp(exp: Int) {
        experience += exp
        // Simple level up system
        val requiredExp = level * 10
        if (experience >= requiredExp) {
            levelUp()
        }
    }

    private fun levelUp() {
        level++
        maxHP += 5
        hp = maxHP
        strength++
        attack++
    }

    override fun die() {
        // Permadeath - game over
    }
}
