package com.hardcoredungeon.levels

import com.hardcoredungeon.actors.mobs.*
import kotlin.random.Random

/**
 * Factory for creating mobs based on dungeon depth
 */
object MobFactory {

    fun createMob(depth: Int): Mob {
        return when (depth) {
            in 1..5 -> createSewersMob()
            in 6..10 -> createPrisonMob()
            in 11..15 -> createCavesMob()
            in 16..20 -> createCityMob()
            else -> createHallsMob()
        }
    }

    private fun createSewersMob(): Mob {
        return when (Random.nextInt(100)) {
            in 0..40 -> Rat()
            in 41..70 -> Crab()
            in 71..90 -> Gnoll()
            else -> Bat()
        }
    }

    private fun createPrisonMob(): Mob {
        return when (Random.nextInt(100)) {
            in 0..30 -> Gnoll()
            in 31..60 -> Skeleton()
            in 61..85 -> Thief()
            else -> Bat()
        }
    }

    private fun createCavesMob(): Mob {
        return when (Random.nextInt(100)) {
            in 0..35 -> Skeleton()
            in 36..65 -> Bat()
            in 66..90 -> Snake()
            else -> Golem()
        }
    }

    private fun createCityMob(): Mob {
        return when (Random.nextInt(100)) {
            in 0..40 -> Snake()
            in 41..70 -> Golem()
            in 71..90 -> Skeleton()
            else -> Thief()
        }
    }

    private fun createHallsMob(): Mob {
        return when (Random.nextInt(100)) {
            in 0..50 -> Golem()
            in 51..80 -> Skeleton()
            else -> DemonLord()
        }
    }

    fun createBoss(depth: Int): Mob {
        return when (depth) {
            5 -> GooBlob()
            10 -> TenguBoss()
            15 -> DemonLord()
            20 -> DemonLord()
            25 -> DemonLord()
            else -> GooBlob()
        }
    }
}
