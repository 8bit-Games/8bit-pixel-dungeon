package com.hardcoredungeon.levels

import com.hardcoredungeon.engine.Point
import kotlin.random.Random

/**
 * A single dungeon level
 */
class Level(val width: Int = 50, val height: Int = 50, val depth: Int = 1) {

    private val tiles: Array<Array<Tile>>
    var entrance: Point = Point(0, 0)
    var exit: Point = Point(0, 0)

    init {
        tiles = Array(height) { Array(width) { Tile(TileType.WALL, true) } }
        generate()
    }

    private fun generate() {
        // Simple room-based generation for now
        val rooms = mutableListOf<Room>()
        val maxRooms = 15
        val minRoomSize = 4
        val maxRoomSize = 10

        // Generate rooms
        for (i in 0 until maxRooms) {
            val w = Random.nextInt(minRoomSize, maxRoomSize + 1)
            val h = Random.nextInt(minRoomSize, maxRoomSize + 1)
            val x = Random.nextInt(1, width - w - 1)
            val y = Random.nextInt(1, height - h - 1)

            val newRoom = Room(x, y, w, h)

            // Check for overlap
            var overlaps = false
            for (room in rooms) {
                if (newRoom.intersects(room)) {
                    overlaps = true
                    break
                }
            }

            if (!overlaps) {
                carveRoom(newRoom)

                // Connect to previous room
                if (rooms.isNotEmpty()) {
                    val prev = rooms.last()
                    connectRooms(prev, newRoom)
                }

                rooms.add(newRoom)
            }
        }

        // Set entrance and exit
        if (rooms.isNotEmpty()) {
            entrance = rooms.first().center()
            exit = rooms.last().center()

            setTile(entrance.x, entrance.y, Tile(TileType.ENTRANCE, false))
            setTile(exit.x, exit.y, Tile(TileType.EXIT, false))
        }
    }

    private fun carveRoom(room: Room) {
        for (y in room.y until room.y + room.height) {
            for (x in room.x until room.x + room.width) {
                setTile(x, y, Tile(TileType.FLOOR, false))
            }
        }
    }

    private fun connectRooms(room1: Room, room2: Room) {
        val start = room1.center()
        val end = room2.center()

        // L-shaped corridor
        if (Random.nextBoolean()) {
            // Horizontal then vertical
            for (x in minOf(start.x, end.x)..maxOf(start.x, end.x)) {
                setTile(x, start.y, Tile(TileType.FLOOR, false))
            }
            for (y in minOf(start.y, end.y)..maxOf(start.y, end.y)) {
                setTile(end.x, y, Tile(TileType.FLOOR, false))
            }
        } else {
            // Vertical then horizontal
            for (y in minOf(start.y, end.y)..maxOf(start.y, end.y)) {
                setTile(start.x, y, Tile(TileType.FLOOR, false))
            }
            for (x in minOf(start.x, end.x)..maxOf(start.x, end.x)) {
                setTile(x, end.y, Tile(TileType.FLOOR, false))
            }
        }
    }

    fun getTile(x: Int, y: Int): Tile {
        if (!isValid(x, y)) return Tile(TileType.WALL, true)
        return tiles[y][x]
    }

    fun setTile(x: Int, y: Int, tile: Tile) {
        if (isValid(x, y)) {
            tiles[y][x] = tile
        }
    }

    fun isValid(x: Int, y: Int): Boolean {
        return x in 0 until width && y in 0 until height
    }
}

data class Room(val x: Int, val y: Int, val width: Int, val height: Int) {
    fun center(): Point = Point(x + width / 2, y + height / 2)

    fun intersects(other: Room): Boolean {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y
    }
}
