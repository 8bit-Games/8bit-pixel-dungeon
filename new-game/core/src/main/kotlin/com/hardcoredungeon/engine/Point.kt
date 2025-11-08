package com.hardcoredungeon.engine

/**
 * Simple 2D point for grid positions
 */
data class Point(var x: Int, var y: Int) {
    fun set(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    fun set(other: Point) {
        this.x = other.x
        this.y = other.y
    }

    fun offset(dx: Int, dy: Int): Point {
        return Point(x + dx, y + dy)
    }

    fun distanceTo(other: Point): Int {
        val dx = x - other.x
        val dy = y - other.y
        return kotlin.math.sqrt((dx * dx + dy * dy).toDouble()).toInt()
    }

    companion object {
        val ZERO = Point(0, 0)
    }
}
