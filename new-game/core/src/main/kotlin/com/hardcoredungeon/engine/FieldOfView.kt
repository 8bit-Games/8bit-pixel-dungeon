package com.hardcoredungeon.engine

import com.hardcoredungeon.levels.Level
import kotlin.math.abs

/**
 * Field of View calculator using shadow casting algorithm
 */
object FieldOfView {

    fun compute(level: Level, origin: Point, radius: Int): Set<Point> {
        val visible = mutableSetOf<Point>()
        visible.add(origin)

        // Cast shadows in 8 octants
        for (octant in 0 until 8) {
            castLight(level, origin, radius, 1, 1.0, 0.0, octant, visible)
        }

        return visible
    }

    private fun castLight(
        level: Level,
        origin: Point,
        radius: Int,
        row: Int,
        startSlope: Double,
        endSlope: Double,
        octant: Int,
        visible: MutableSet<Point>
    ) {
        if (startSlope < endSlope) return

        var newStart = 0.0
        var blocked = false

        for (distance in row..radius) {
            var deltaY = -distance
            while (deltaY <= 0) {
                val deltaX = -distance
                val currentX = origin.x + transformX(deltaX, deltaY, octant)
                val currentY = origin.y + transformY(deltaX, deltaY, octant)

                val leftSlope = (deltaY - 0.5) / (deltaX + 0.5)
                val rightSlope = (deltaY + 0.5) / (deltaX - 0.5)

                if (startSlope < rightSlope) {
                    deltaY++
                    continue
                } else if (endSlope > leftSlope) {
                    break
                }

                if (!level.isValid(currentX, currentY)) {
                    deltaY++
                    continue
                }

                if (abs(deltaX) + abs(deltaY) <= radius) {
                    visible.add(Point(currentX, currentY))
                }

                val tile = level.getTile(currentX, currentY)
                if (blocked) {
                    if (tile.solid) {
                        newStart = rightSlope
                    } else {
                        blocked = false
                        startSlope = newStart
                    }
                } else if (tile.solid && distance < radius) {
                    blocked = true
                    castLight(level, origin, radius, distance + 1, startSlope, leftSlope, octant, visible)
                    newStart = rightSlope
                }

                deltaY++
            }

            if (blocked) break
        }
    }

    private fun transformX(x: Int, y: Int, octant: Int): Int {
        return when (octant) {
            0 -> x
            1 -> y
            2 -> -y
            3 -> -x
            4 -> -x
            5 -> -y
            6 -> y
            7 -> x
            else -> 0
        }
    }

    private fun transformY(x: Int, y: Int, octant: Int): Int {
        return when (octant) {
            0 -> y
            1 -> x
            2 -> x
            3 -> y
            4 -> -y
            5 -> -x
            6 -> -x
            7 -> -y
            else -> 0
        }
    }
}
