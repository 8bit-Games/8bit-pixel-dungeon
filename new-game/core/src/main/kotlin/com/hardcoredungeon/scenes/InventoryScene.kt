package com.hardcoredungeon.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.hardcoredungeon.HardcoreDungeon
import com.hardcoredungeon.actors.hero.Hero
import com.hardcoredungeon.items.Equipable
import com.hardcoredungeon.items.Item
import com.hardcoredungeon.items.consumables.Food
import com.hardcoredungeon.items.consumables.Potion
import com.hardcoredungeon.items.consumables.Scroll

/**
 * Inventory screen where players can view and use items
 */
class InventoryScene(game: HardcoreDungeon, private val hero: Hero, private val previousScene: GameScene) : BaseScene(game) {

    private val batch = SpriteBatch()
    private val font = BitmapFont()
    private var selectedIndex = 0

    init {
        font.color = Color.WHITE
        font.data.setScale(1.2f)
    }

    override fun render(delta: Float) {
        batch.begin()

        val screenWidth = Gdx.graphics.width.toFloat()
        val screenHeight = Gdx.graphics.height.toFloat()
        var y = screenHeight - 50f

        // Title
        font.color = Color.YELLOW
        font.data.setScale(2f)
        font.draw(batch, "INVENTORY", screenWidth / 2 - 80, y)
        font.data.setScale(1.2f)
        y -= 50f

        // Stats
        font.color = Color.WHITE
        font.draw(batch, "Gold: ${hero.gold}  |  ${hero.inventory.size()}/${hero.inventory.maxSize} items", 20f, y)
        y -= 40f

        // Equipment
        font.color = Color.CYAN
        font.draw(batch, "=== EQUIPMENT ===", 20f, y)
        y -= 30f

        font.color = Color.WHITE
        val weapon = hero.inventory.getEquipped(com.hardcoredungeon.items.EquipSlot.WEAPON)
        font.draw(batch, "Weapon: ${weapon?.name ?: "None"}", 40f, y)
        y -= 25f

        val armor = hero.inventory.getEquipped(com.hardcoredungeon.items.EquipSlot.ARMOR)
        font.draw(batch, "Armor: ${armor?.name ?: "None"}", 40f, y)
        y -= 25f

        val ring1 = hero.inventory.getEquipped(com.hardcoredungeon.items.EquipSlot.RING1)
        font.draw(batch, "Ring 1: ${ring1?.name ?: "None"}", 40f, y)
        y -= 25f

        val ring2 = hero.inventory.getEquipped(com.hardcoredungeon.items.EquipSlot.RING2)
        font.draw(batch, "Ring 2: ${ring2?.name ?: "None"}", 40f, y)
        y -= 40f

        // Items
        font.color = Color.CYAN
        font.draw(batch, "=== ITEMS ===", 20f, y)
        y -= 30f

        val items = hero.inventory.getItems()
        if (items.isEmpty()) {
            font.color = Color.GRAY
            font.draw(batch, "Your inventory is empty", 40f, y)
        } else {
            items.forEachIndexed { index, item ->
                val prefix = if (index == selectedIndex) "> " else "  "
                val color = when (index) {
                    selectedIndex -> Color.YELLOW
                    else -> Color.WHITE
                }

                font.color = color
                val itemText = buildItemText(item)
                font.draw(batch, "$prefix$itemText", 40f, y)
                y -= 25f
            }
        }

        // Controls
        y = 60f
        font.color = Color.GRAY
        font.data.setScale(1f)
        font.draw(batch, "[UP/DOWN] Select  [E] Equip/Use  [D] Drop  [ESC] Back", 20f, y)

        batch.end()

        // Handle input
        handleInput(items)
    }

    private fun buildItemText(item: Item): String {
        val nameText = item.name
        val quantityText = if (item.stackable && item.quantity > 1) " x${item.quantity}" else ""
        val equipText = if (item is Equipable && item.equipped) " [EQUIPPED]" else ""
        val typeText = when (item) {
            is Food -> " (Food)"
            is Potion -> " (Potion)"
            is Scroll -> " (Scroll)"
            is Equipable -> " (${item.slot.name})"
            else -> ""
        }

        return "$nameText$quantityText$typeText$equipText"
    }

    private fun handleInput(items: List<Item>) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (items.isNotEmpty()) {
                selectedIndex = (selectedIndex - 1 + items.size) % items.size
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if (items.isNotEmpty()) {
                selectedIndex = (selectedIndex + 1) % items.size
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && items.isNotEmpty()) {
            val item = items.getOrNull(selectedIndex)
            if (item != null) {
                useOrEquipItem(item)
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D) && items.isNotEmpty()) {
            val item = items.getOrNull(selectedIndex)
            if (item != null) {
                dropItem(item)
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.screen = previousScene
        }
    }

    private fun useOrEquipItem(item: Item) {
        when (item) {
            is Equipable -> {
                if (item.equipped) {
                    hero.inventory.unequip(item, hero)
                    Gdx.app.log("Inventory", "Unequipped ${item.name}")
                } else {
                    hero.inventory.equip(item, hero)
                    Gdx.app.log("Inventory", "Equipped ${item.name}")
                }
            }
            is Potion, is Scroll, is Food -> {
                item.use(hero)
                if (item.quantity <= 0) {
                    hero.inventory.remove(item)
                }
                Gdx.app.log("Inventory", "Used ${item.name}")
            }
        }
    }

    private fun dropItem(item: Item) {
        hero.inventory.remove(item)
        Gdx.app.log("Inventory", "Dropped ${item.name}")
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
    }
}
