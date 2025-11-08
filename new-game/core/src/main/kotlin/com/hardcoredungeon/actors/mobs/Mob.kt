package com.hardcoredungeon.actors.mobs

import com.hardcoredungeon.actors.Actor
import com.hardcoredungeon.actors.hero.Hero
import com.hardcoredungeon.engine.Point
import com.hardcoredungeon.levels.Level
import kotlin.random.Random

/**
 * Base class for all enemy mobs
 */
abstract class Mob(
    val name: String,
    baseHP: Int,
    val attack: Int,
    val defense: Int,
    val expValue: Int
) : Actor() {

    var state: MobState = MobState.WANDERING

    init {
        maxHP = baseHP
        hp = maxHP
    }

    fun actTurn(hero: Hero, level: Level): Boolean {
        if (!isAlive) return false

        // Check if hero is nearby
        val distance = pos.distanceTo(hero.pos)

        state = when {
            distance <= 1 -> MobState.ATTACKING
            distance <= 6 -> MobState.HUNTING
            else -> MobState.WANDERING
        }

        return when (state) {
            MobState.ATTACKING -> attackHero(hero)
            MobState.HUNTING -> moveTowards(hero.pos, level)
            MobState.WANDERING -> wander(level)
        }
    }

    private fun attackHero(hero: Hero): Boolean {
        val damage = maxOf(1, attack - hero.defense)
        hero.takeDamage(damage)
        return true
    }

    private fun moveTowards(target: Point, level: Level): Boolean {
        val dx = when {
            target.x > pos.x -> 1
            target.x < pos.x -> -1
            else -> 0
        }

        val dy = when {
            target.y > pos.y -> 1
            target.y < pos.y -> -1
            else -> 0
        }

        return tryMove(dx, dy, level)
    }

    private fun wander(level: Level): Boolean {
        if (Random.nextFloat() > 0.3f) return false

        val dx = Random.nextInt(-1, 2)
        val dy = Random.nextInt(-1, 2)

        return tryMove(dx, dy, level)
    }

    private fun tryMove(dx: Int, dy: Int, level: Level): Boolean {
        val newPos = pos.offset(dx, dy)

        if (!level.isValid(newPos.x, newPos.y)) return false

        val tile = level.getTile(newPos.x, newPos.y)
        if (tile.solid) return false

        pos = newPos
        return true
    }

    override fun die() {
        // Drop loot, give exp, etc.
    }
}

enum class MobState {
    WANDERING,
    HUNTING,
    ATTACKING
}
