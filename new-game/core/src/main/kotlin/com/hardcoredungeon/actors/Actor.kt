package com.hardcoredungeon.actors

import com.hardcoredungeon.engine.Point

/**
 * Base class for all game entities (hero, mobs, etc.)
 */
abstract class Actor {
    var pos: Point = Point(0, 0)
    var hp: Int = 0
    var maxHP: Int = 0

    open fun act(): Boolean {
        return false
    }

    open fun takeDamage(damage: Int) {
        hp -= damage
        if (hp <= 0) {
            die()
        }
    }

    open fun die() {
        // Override in subclasses
    }

    val isAlive: Boolean
        get() = hp > 0
}
