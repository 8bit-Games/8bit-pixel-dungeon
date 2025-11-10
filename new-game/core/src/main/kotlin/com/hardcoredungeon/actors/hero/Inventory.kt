package com.hardcoredungeon.actors.hero

import com.hardcoredungeon.items.Equipable
import com.hardcoredungeon.items.EquipSlot
import com.hardcoredungeon.items.Item

/**
 * Hero's inventory and equipment management
 */
class Inventory(val maxSize: Int = 20) {

    private val items = mutableListOf<Item>()
    private val equipment = mutableMapOf<EquipSlot, Equipable>()

    fun add(item: Item): Boolean {
        if (items.size >= maxSize) {
            return false
        }

        // Stack if possible
        if (item.stackable) {
            val existing = items.find { it.name == item.name }
            if (existing != null) {
                existing.quantity += item.quantity
                return true
            }
        }

        items.add(item)
        return true
    }

    fun remove(item: Item): Boolean {
        return items.remove(item)
    }

    fun equip(item: Equipable, hero: Hero): Boolean {
        if (item !in items) return false

        // Unequip existing item in slot
        val currentItem = equipment[item.slot]
        if (currentItem != null) {
            unequip(currentItem, hero)
        }

        equipment[item.slot] = item
        item.onEquip(hero)
        return true
    }

    fun unequip(item: Equipable, hero: Hero): Boolean {
        if (equipment[item.slot] != item) return false

        equipment.remove(item.slot)
        item.onUnequip(hero)
        return true
    }

    fun getEquipped(slot: EquipSlot): Equipable? {
        return equipment[slot]
    }

    fun getItems(): List<Item> {
        return items.toList()
    }

    fun size(): Int {
        return items.size
    }

    fun isFull(): Boolean {
        return items.size >= maxSize
    }

    fun contains(item: Item): Boolean {
        return item in items
    }

    fun getByName(name: String): Item? {
        return items.find { it.name == name }
    }
}
