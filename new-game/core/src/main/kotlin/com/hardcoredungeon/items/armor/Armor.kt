package com.hardcoredungeon.items.armor

import com.hardcoredungeon.actors.hero.Hero
import com.hardcoredungeon.items.Equipable
import com.hardcoredungeon.items.EquipSlot

/**
 * Armor items
 */
abstract class Armor(
    name: String,
    val defense: Int,
    val strengthReq: Int
) : Equipable(name, EquipSlot.ARMOR) {

    override fun onEquip(hero: Hero) {
        super.onEquip(hero)
        hero.defense += defense
    }

    override fun onUnequip(hero: Hero) {
        super.onUnequip(hero)
        hero.defense -= defense
    }
}

class ClothArmor : Armor("Cloth Armor", 1, 8)
class LeatherArmor : Armor("Leather Armor", 2, 10)
class MailArmor : Armor("Mail Armor", 3, 12)
class ScaleArmor : Armor("Scale Armor", 4, 14)
class PlateArmor : Armor("Plate Armor", 5, 16)
