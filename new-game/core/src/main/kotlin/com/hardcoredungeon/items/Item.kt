package com.hardcoredungeon.items

import com.hardcoredungeon.actors.hero.Hero
import com.hardcoredungeon.engine.Point

/**
 * Base class for all items
 */
abstract class Item(
    val name: String,
    val stackable: Boolean = false
) {
    var quantity: Int = 1
    var identified: Boolean = false
    var pos: Point = Point(0, 0)

    open fun use(hero: Hero): Boolean {
        return false
    }

    open fun info(): String {
        return name
    }
}

/**
 * Equipable items (weapons, armor, rings)
 */
abstract class Equipable(
    name: String,
    val slot: EquipSlot
) : Item(name, false) {

    var equipped: Boolean = false

    open fun onEquip(hero: Hero) {
        equipped = true
    }

    open fun onUnequip(hero: Hero) {
        equipped = false
    }
}

enum class EquipSlot {
    WEAPON,
    ARMOR,
    RING1,
    RING2
}
