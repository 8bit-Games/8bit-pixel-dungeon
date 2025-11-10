package com.hardcoredungeon.levels

/**
 * Individual tile in the dungeon
 */
data class Tile(
    val type: TileType,
    val solid: Boolean,
    val water: Boolean = false,
    val discovered: Boolean = false
)

enum class TileType {
    FLOOR,
    WALL,
    WATER,
    DOOR,
    ENTRANCE,
    EXIT,
    CHASM
}
