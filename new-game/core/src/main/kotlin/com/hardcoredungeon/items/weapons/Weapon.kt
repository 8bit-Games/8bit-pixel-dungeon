package com.hardcoredungeon.items.weapons

import com.hardcoredungeon.actors.hero.Hero
import com.hardcoredungeon.items.Equipable
import com.hardcoredungeon.items.EquipSlot

/**
 * Base weapon class
 */
abstract class Weapon(
    name: String,
    val minDamage: Int,
    val maxDamage: Int,
    val strengthReq: Int
) : Equipable(name, EquipSlot.WEAPON) {

    fun damage(): Int {
        return (minDamage..maxDamage).random()
    }

    override fun onEquip(hero: Hero) {
        super.onEquip(hero)
        hero.attack += (minDamage + maxDamage) / 2
    }

    override fun onUnequip(hero: Hero) {
        super.onUnequip(hero)
        hero.attack -= (minDamage + maxDamage) / 2
    }
}

// Melee weapons
class Dagger : Weapon("Dagger", 1, 4, 8)
class Sword : Weapon("Sword", 3, 8, 10)
class Mace : Weapon("Mace", 4, 10, 12)
class Axe : Weapon("Battle Axe", 5, 12, 14)
class Spear : Weapon("Spear", 3, 9, 10)
class Glaive : Weapon("Glaive", 6, 14, 15)

// Ranged weapons
class Bow : Weapon("Bow", 2, 6, 9)
class Crossbow : Weapon("Crossbow", 4, 8, 11)
