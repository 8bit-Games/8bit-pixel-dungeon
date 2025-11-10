package com.hardcoredungeon.engine

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class PointTest {

    @Test
    fun testPointCreation() {
        val point = Point(5, 10)
        assertEquals(5, point.x)
        assertEquals(10, point.y)
    }

    @Test
    fun testPointSet() {
        val point = Point(0, 0)
        point.set(3, 4)
        assertEquals(3, point.x)
        assertEquals(4, point.y)
    }

    @Test
    fun testPointOffset() {
        val point = Point(5, 10)
        val offset = point.offset(2, 3)
        assertEquals(7, offset.x)
        assertEquals(13, offset.y)
        // Original should not change
        assertEquals(5, point.x)
        assertEquals(10, point.y)
    }

    @Test
    fun testPointDistance() {
        val p1 = Point(0, 0)
        val p2 = Point(3, 4)
        // 3-4-5 triangle
        assertEquals(5, p1.distanceTo(p2))
    }

    @Test
    fun testPointEquality() {
        val p1 = Point(5, 10)
        val p2 = Point(5, 10)
        val p3 = Point(3, 4)

        assertEquals(p1, p2)
        assertNotEquals(p1, p3)
    }
}
