package com.hardcoredungeon.engine

import com.hardcoredungeon.actors.hero.Hero
import com.hardcoredungeon.levels.Level

/**
 * Manages the dungeon state and progression
 */
class Dungeon(val hero: Hero) {

    var depth: Int = 1
    var currentLevel: Level

    init {
        currentLevel = generateLevel()
    }

    private fun generateLevel(): Level {
        return Level(depth = depth)
    }

    fun descendLevel() {
        depth++
        currentLevel = generateLevel()
        hero.pos = currentLevel.entrance
    }

    fun ascendLevel() {
        if (depth > 1) {
            depth--
            currentLevel = generateLevel()
            hero.pos = currentLevel.exit
        }
    }
}
