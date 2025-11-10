package com.hardcoredungeon.items.consumables

import com.hardcoredungeon.actors.hero.Hero
import com.hardcoredungeon.items.Item

/**
 * Potions - single use consumables
 */
abstract class Potion(name: String) : Item(name, true) {

    override fun use(hero: Hero): Boolean {
        quantity--
        return true
    }
}

class PotionOfHealing : Potion("Potion of Healing") {
    override fun use(hero: Hero): Boolean {
        hero.hp = minOf(hero.hp + hero.maxHP / 2, hero.maxHP)
        return super.use(hero)
    }
}

class PotionOfStrength : Potion("Potion of Strength") {
    override fun use(hero: Hero): Boolean {
        hero.strength++
        return super.use(hero)
    }
}

class PotionOfExperience : Potion("Potion of Experience") {
    override fun use(hero: Hero): Boolean {
        hero.gainExp(hero.level * 10)
        return super.use(hero)
    }
}

class PotionOfMight : Potion("Potion of Might") {
    override fun use(hero: Hero): Boolean {
        hero.maxHP += 5
        hero.hp += 5
        hero.strength++
        return super.use(hero)
    }
}

class PotionOfInvisibility : Potion("Potion of Invisibility") {
    override fun use(hero: Hero): Boolean {
        // Add invisibility buff
        return super.use(hero)
    }
}

class PotionOfLiquidFlame : Potion("Potion of Liquid Flame") {
    override fun use(hero: Hero): Boolean {
        // Throw to create fire
        return super.use(hero)
    }
}
