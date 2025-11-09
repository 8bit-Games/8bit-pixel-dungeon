package com.hardcoredungeon.levels

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class LevelTest {

    @Test
    fun testLevelGeneration() {
        val level = Level(width = 50, height = 50, depth = 1)

        assertEquals(50, level.width)
        assertEquals(50, level.height)
        assertEquals(1, level.depth)

        // Should have entrance and exit
        assertNotNull(level.entrance)
        assertNotNull(level.exit)

        // Entrance should be walkable
        val entranceTile = level.getTile(level.entrance.x, level.entrance.y)
        assertTrue(!entranceTile.solid)

        // Exit should be walkable
        val exitTile = level.getTile(level.exit.x, level.exit.y)
        assertTrue(!exitTile.solid)
    }

    @Test
    fun testLevelHasRooms() {
        val level = Level(depth = 1)

        // Should have generated some rooms
        assertTrue(level.rooms.isNotEmpty())
        assertTrue(level.rooms.size <= 15) // Max rooms is 15
    }

    @Test
    fun testMobSpawning() {
        val level = Level(depth = 1)

        // Should have spawned some mobs
        assertTrue(level.mobs.isNotEmpty())
        assertTrue(level.mobs.size in 8..15) // Between 8 and 15 mobs

        // All mobs should have valid positions
        for (mob in level.mobs) {
            assertTrue(level.isValid(mob.pos.x, mob.pos.y))
            val tile = level.getTile(mob.pos.x, mob.pos.y)
            assertTrue(!tile.solid)
        }
    }

    @Test
    fun testItemSpawning() {
        val level = Level(depth = 1)

        // Should have spawned some items
        assertTrue(level.items.isNotEmpty())
        assertTrue(level.items.size in 3..8) // Between 3 and 8 items

        // All items should have valid positions
        for (item in level.items) {
            assertTrue(level.isValid(item.pos.x, item.pos.y))
            val tile = level.getTile(item.pos.x, item.pos.y)
            assertTrue(!tile.solid)
        }
    }

    @Test
    fun testGetMobAt() {
        val level = Level(depth = 1)

        // If there are mobs, test getting one
        if (level.mobs.isNotEmpty()) {
            val mob = level.mobs.first()
            val foundMob = level.getMobAt(mob.pos.x, mob.pos.y)
            assertNotNull(foundMob)
            assertEquals(mob, foundMob)
        }
    }

    @Test
    fun testGetItemAt() {
        val level = Level(depth = 1)

        // If there are items, test getting one
        if (level.items.isNotEmpty()) {
            val item = level.items.first()
            val foundItem = level.getItemAt(item.pos.x, item.pos.y)
            assertNotNull(foundItem)
            assertEquals(item, foundItem)
        }
    }

    @Test
    fun testIsValid() {
        val level = Level(width = 50, height = 50, depth = 1)

        assertTrue(level.isValid(0, 0))
        assertTrue(level.isValid(25, 25))
        assertTrue(level.isValid(49, 49))

        assertTrue(!level.isValid(-1, 0))
        assertTrue(!level.isValid(0, -1))
        assertTrue(!level.isValid(50, 0))
        assertTrue(!level.isValid(0, 50))
    }

    @Test
    fun testGetRandomFloorTile() {
        val level = Level(depth = 1)

        val tile = level.getRandomFloorTile()
        assertNotNull(tile)

        val tileType = level.getTile(tile.x, tile.y)
        assertTrue(!tileType.solid)
    }
}
