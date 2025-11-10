package com.hardcoredungeon.actors.buffs

import com.hardcoredungeon.actors.Actor

/**
 * Status effects (buffs and debuffs)
 */
abstract class Buff(
    val name: String,
    var duration: Int = -1  // -1 = permanent, > 0 = temporary
) {
    var target: Actor? = null

    open fun attach(actor: Actor) {
        target = actor
        onAttach()
    }

    open fun detach() {
        onDetach()
        target = null
    }

    open fun act(): Boolean {
        if (duration > 0) {
            duration--
            if (duration == 0) {
                detach()
                return false
            }
        }
        return true
    }

    protected open fun onAttach() {}
    protected open fun onDetach() {}
}

/**
 * Positive buffs
 */
class Strength(duration: Int = -1) : Buff("Strength", duration) {
    override fun onAttach() {
        // Increase attack
    }

    override fun onDetach() {
        // Decrease attack
    }
}

class Invisibility(duration: Int = 20) : Buff("Invisibility", duration) {
    override fun onAttach() {
        // Mobs can't see you
    }
}

class Haste(duration: Int = 15) : Buff("Haste", duration) {
    override fun onAttach() {
        // Move twice as fast
    }
}

class Levitation(duration: Int = 15) : Buff("Levitation", duration) {
    override fun onAttach() {
        // Can walk over chasms and water
    }
}

class MindVision(duration: Int = 20) : Buff("Mind Vision", duration) {
    override fun onAttach() {
        // See all mobs on level
    }
}

/**
 * Negative debuffs
 */
class Poison(duration: Int = 10) : Buff("Poison", duration) {
    override fun act(): Boolean {
        target?.takeDamage(1)
        return super.act()
    }
}

class Burning(duration: Int = 5) : Buff("Burning", duration) {
    override fun act(): Boolean {
        target?.takeDamage(2)
        return super.act()
    }
}

class Bleeding(duration: Int = 10) : Buff("Bleeding", duration) {
    override fun act(): Boolean {
        target?.takeDamage(1)
        return super.act()
    }
}

class Paralysis(duration: Int = 5) : Buff("Paralysis", duration) {
    override fun onAttach() {
        // Can't move
    }
}

class Slow(duration: Int = 10) : Buff("Slow", duration) {
    override fun onAttach() {
        // Move at half speed
    }
}

class Blindness(duration: Int = 10) : Buff("Blindness", duration) {
    override fun onAttach() {
        // Reduced vision
    }
}

class Weakness(duration: Int = 15) : Buff("Weakness", duration) {
    override fun onAttach() {
        // Reduced attack
    }
}

class Cripple(duration: Int = 8) : Buff("Cripple", duration) {
    override fun onAttach() {
        // Can't move
    }
}

class Terror(duration: Int = 10) : Buff("Terror", duration) {
    override fun onAttach() {
        // Run away from hero
    }
}
