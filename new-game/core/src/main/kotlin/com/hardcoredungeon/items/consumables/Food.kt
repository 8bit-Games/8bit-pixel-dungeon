package com.hardcoredungeon.items.consumables

import com.hardcoredungeon.actors.hero.Hero
import com.hardcoredungeon.items.Item

/**
 * Food items to restore hunger
 */
open class Food(name: String, val hungerValue: Int) : Item(name, true) {

    override fun use(hero: Hero): Boolean {
        hero.hunger = minOf(hero.hunger + hungerValue, 1000)
        quantity--
        return true
    }
}

class Ration : Food("Ration", 400)
class Bread : Food("Bread", 200)
class Meat : Food("Meat", 300)
class Fruit : Food("Fruit", 150)

// Convenience function for factory
fun Food(): Item = when ((0..3).random()) {
    0 -> Ration()
    1 -> Bread()
    2 -> Meat()
    else -> Fruit()
}
