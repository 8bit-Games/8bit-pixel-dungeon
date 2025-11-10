package com.hardcoredungeon.items.consumables

import com.hardcoredungeon.actors.hero.Hero
import com.hardcoredungeon.items.Item

/**
 * Scrolls - single use consumables
 */
abstract class Scroll(name: String) : Item(name, true) {

    override fun use(hero: Hero): Boolean {
        quantity--
        return true
    }
}

class ScrollOfIdentify : Scroll("Scroll of Identify") {
    override fun use(hero: Hero): Boolean {
        // Identify an item
        return super.use(hero)
    }
}

class ScrollOfUpgrade : Scroll("Scroll of Upgrade") {
    override fun use(hero: Hero): Boolean {
        // Upgrade an item
        return super.use(hero)
    }
}

class ScrollOfTeleportation : Scroll("Scroll of Teleportation") {
    override fun use(hero: Hero): Boolean {
        // Teleport randomly
        return super.use(hero)
    }
}

class ScrollOfMagicMapping : Scroll("Scroll of Magic Mapping") {
    override fun use(hero: Hero): Boolean {
        // Reveal map
        return super.use(hero)
    }
}

class ScrollOfRemoveCurse : Scroll("Scroll of Remove Curse") {
    override fun use(hero: Hero): Boolean {
        // Remove curses from equipment
        return super.use(hero)
    }
}

class ScrollOfTerror : Scroll("Scroll of Terror") {
    override fun use(hero: Hero): Boolean {
        // Frighten nearby enemies
        return super.use(hero)
    }
}
