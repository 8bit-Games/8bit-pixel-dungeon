package com.hardcoredungeon.levels

import com.hardcoredungeon.items.Item
import com.hardcoredungeon.items.armor.*
import com.hardcoredungeon.items.consumables.*
import com.hardcoredungeon.items.weapons.*
import kotlin.random.Random

/**
 * Factory for creating random items based on dungeon depth
 */
object ItemFactory {

    fun createRandomItem(depth: Int): Item {
        return when (Random.nextInt(100)) {
            in 0..40 -> createPotion()
            in 41..60 -> createScroll()
            in 61..80 -> createWeapon(depth)
            in 81..95 -> createArmor(depth)
            else -> createFood()
        }
    }

    private fun createPotion(): Item {
        return when (Random.nextInt(6)) {
            0 -> PotionOfHealing()
            1 -> PotionOfStrength()
            2 -> PotionOfExperience()
            3 -> PotionOfMight()
            4 -> PotionOfInvisibility()
            else -> PotionOfLiquidFlame()
        }
    }

    private fun createScroll(): Item {
        return when (Random.nextInt(6)) {
            0 -> ScrollOfIdentify()
            1 -> ScrollOfUpgrade()
            2 -> ScrollOfTeleportation()
            3 -> ScrollOfMagicMapping()
            4 -> ScrollOfRemoveCurse()
            else -> ScrollOfTerror()
        }
    }

    private fun createWeapon(depth: Int): Item {
        return when {
            depth < 5 -> when (Random.nextInt(3)) {
                0 -> Dagger()
                1 -> Spear()
                else -> Bow()
            }
            depth < 10 -> when (Random.nextInt(3)) {
                0 -> Sword()
                1 -> Mace()
                else -> Crossbow()
            }
            depth < 15 -> when (Random.nextInt(2)) {
                0 -> Axe()
                else -> Glaive()
            }
            else -> Glaive()
        }
    }

    private fun createArmor(depth: Int): Item {
        return when {
            depth < 5 -> if (Random.nextBoolean()) ClothArmor() else LeatherArmor()
            depth < 10 -> if (Random.nextBoolean()) LeatherArmor() else MailArmor()
            depth < 15 -> if (Random.nextBoolean()) MailArmor() else ScaleArmor()
            else -> if (Random.nextBoolean()) ScaleArmor() else PlateArmor()
        }
    }

    private fun createFood(): Item {
        return Food()
    }
}
