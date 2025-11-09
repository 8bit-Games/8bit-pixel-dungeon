package com.hardcoredungeon.actors

import com.hardcoredungeon.actors.hero.Hero
import com.hardcoredungeon.actors.hero.HeroClass
import com.hardcoredungeon.actors.hero.Inventory
import com.hardcoredungeon.items.consumables.PotionOfHealing
import com.hardcoredungeon.items.weapons.Sword
import com.hardcoredungeon.items.armor.PlateArmor
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InventoryTest {

    @Test
    fun testInventoryCreation() {
        val inventory = Inventory(maxSize = 20)
        assertEquals(0, inventory.size())
        assertTrue(!inventory.isFull())
    }

    @Test
    fun testAddItem() {
        val inventory = Inventory(maxSize = 20)
        val potion = PotionOfHealing()

        assertTrue(inventory.add(potion))
        assertEquals(1, inventory.size())
        assertTrue(inventory.contains(potion))
    }

    @Test
    fun testRemoveItem() {
        val inventory = Inventory(maxSize = 20)
        val potion = PotionOfHealing()

        inventory.add(potion)
        assertTrue(inventory.remove(potion))
        assertEquals(0, inventory.size())
        assertTrue(!inventory.contains(potion))
    }

    @Test
    fun testInventoryFull() {
        val inventory = Inventory(maxSize = 2)

        inventory.add(PotionOfHealing())
        inventory.add(PotionOfHealing())

        assertTrue(inventory.isFull())

        val result = inventory.add(PotionOfHealing())
        assertTrue(!result) // Should fail to add
        assertEquals(2, inventory.size())
    }

    @Test
    fun testEquipItem() {
        val hero = Hero(HeroClass.WARRIOR)
        val sword = Sword()

        hero.inventory.add(sword)
        val initialAttack = hero.attack

        hero.inventory.equip(sword, hero)
        assertTrue(hero.attack > initialAttack)
    }

    @Test
    fun testUnequipItem() {
        val hero = Hero(HeroClass.WARRIOR)
        val armor = PlateArmor()

        hero.inventory.add(armor)
        val initialDefense = hero.defense

        hero.inventory.equip(armor, hero)
        val equippedDefense = hero.defense

        assertTrue(equippedDefense > initialDefense)

        hero.inventory.unequip(armor, hero)
        assertEquals(initialDefense, hero.defense)
    }

    @Test
    fun testStackableItems() {
        val inventory = Inventory(maxSize = 20)
        val potion1 = PotionOfHealing()
        val potion2 = PotionOfHealing()

        inventory.add(potion1)
        inventory.add(potion2)

        // Should stack into one item with quantity 2
        assertEquals(1, inventory.size())
        assertEquals(2, potion1.quantity)
    }
}
