package com.hardcoredungeon.actors

import com.hardcoredungeon.actors.hero.Hero
import com.hardcoredungeon.actors.hero.HeroClass
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HeroTest {

    @Test
    fun testHeroCreation() {
        val hero = Hero(HeroClass.WARRIOR)

        assertEquals(HeroClass.WARRIOR.baseHP, hero.maxHP)
        assertEquals(HeroClass.WARRIOR.baseHP, hero.hp)
        assertEquals(HeroClass.WARRIOR.baseStr, hero.strength)
        assertEquals(HeroClass.WARRIOR.baseAttack, hero.attack)
        assertEquals(HeroClass.WARRIOR.baseDefense, hero.defense)
        assertEquals(1000, hero.hunger)
        assertEquals(0, hero.gold)
        assertEquals(1, hero.level)
    }

    @Test
    fun testHeroLevelUp() {
        val hero = Hero(HeroClass.MAGE)
        val initialHP = hero.maxHP
        val initialStr = hero.strength
        val initialAttack = hero.attack

        // Gain enough exp to level up
        hero.gainExp(10)

        assertEquals(2, hero.level)
        assertEquals(initialHP + 5, hero.maxHP)
        assertEquals(initialStr + 1, hero.strength)
        assertEquals(initialAttack + 1, hero.attack)
        // HP should be fully restored on level up
        assertEquals(hero.maxHP, hero.hp)
    }

    @Test
    fun testHeroTakeDamage() {
        val hero = Hero(HeroClass.ROGUE)
        val initialHP = hero.hp

        hero.takeDamage(5)
        assertEquals(initialHP - 5, hero.hp)

        assertTrue(hero.isAlive)

        hero.takeDamage(1000)
        assertTrue(!hero.isAlive)
    }

    @Test
    fun testInventoryStartsEmpty() {
        val hero = Hero(HeroClass.RANGER)
        assertEquals(0, hero.inventory.size())
    }

    @Test
    fun testBuffsStartEmpty() {
        val hero = Hero(HeroClass.WARRIOR)
        assertTrue(hero.buffs.isEmpty())
    }
}
