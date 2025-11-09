package com.hardcoredungeon.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.hardcoredungeon.HardcoreDungeon
import com.hardcoredungeon.actors.hero.HeroClass
import com.hardcoredungeon.nft.NFTSkinManager
import com.hardcoredungeon.nft.Rarity

/**
 * Skin selection screen - Choose cosmetic NFT skins or use defaults
 *
 * This screen shows:
 * 1. Default skin (always available)
 * 2. Owned NFT skins (can equip)
 * 3. Marketplace skins (can buy - if wallet connected)
 */
class SkinSelectionScene(
    game: HardcoreDungeon,
    private val heroClass: HeroClass
) : BaseScene(game) {

    private val batch = SpriteBatch()
    private val font = BitmapFont()
    private var selectedTab = Tab.OWNED
    private var selectedIndex = 0

    enum class Tab {
        OWNED,      // Skins player owns
        MARKETPLACE // Skins available to buy
    }

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
        font.draw(batch, "${heroClass.title} SKINS", screenWidth / 2 - 150, y)
        font.data.setScale(1.2f)
        y -= 60f

        // Wallet status
        if (NFTSkinManager.isWalletConnected()) {
            font.color = Color.GREEN
            val address = NFTSkinManager.getWalletAddress()!!
            val shortAddress = "${address.take(6)}...${address.takeLast(4)}"
            font.draw(batch, "Wallet: $shortAddress", screenWidth - 200, y)
        } else {
            font.color = Color.GRAY
            font.draw(batch, "No Wallet Connected", screenWidth - 220, y)
        }
        y -= 40f

        // Tabs
        font.color = if (selectedTab == Tab.OWNED) Color.YELLOW else Color.GRAY
        font.draw(batch, "[1] My Skins", 40f, y)

        font.color = if (selectedTab == Tab.MARKETPLACE) Color.YELLOW else Color.GRAY
        font.draw(batch, "[2] Marketplace", 200f, y)
        y -= 40f

        // Content based on selected tab
        when (selectedTab) {
            Tab.OWNED -> renderOwnedSkins(y)
            Tab.MARKETPLACE -> renderMarketplace(y)
        }

        // Footer controls
        font.color = Color.GRAY
        font.data.setScale(1f)
        var footerText = "[1/2] Switch Tab  [UP/DOWN] Select  "

        if (selectedTab == Tab.OWNED) {
            footerText += "[E] Equip  [U] Unequip  "
        } else {
            footerText += "[B] Buy  "
        }

        footerText += if (!NFTSkinManager.isWalletConnected()) {
            "[W] Connect Wallet  "
        } else {
            ""
        }

        footerText += "[ESC] Back"

        font.draw(batch, footerText, 20f, 40f)

        batch.end()

        handleInput()
    }

    private fun renderOwnedSkins(startY: Float) {
        var y = startY

        // Always show default skin first
        val isDefaultActive = NFTSkinManager.getActiveSkin(heroClass) == null

        font.color = if (selectedIndex == 0 && selectedTab == Tab.OWNED) Color.YELLOW else Color.WHITE
        val prefix = if (selectedIndex == 0 && selectedTab == Tab.OWNED) "> " else "  "
        val activeText = if (isDefaultActive) " [EQUIPPED]" else ""
        font.draw(batch, "${prefix}Default ${heroClass.title}${activeText}", 60f, y)

        font.color = Color.GRAY
        font.data.setScale(1f)
        font.draw(batch, "Always available - Classic look", 80f, y - 20)
        font.data.setScale(1.2f)
        y -= 50f

        // Show owned NFT skins
        val ownedSkins = NFTSkinManager.getOwnedSkins(heroClass)

        if (ownedSkins.isEmpty() && NFTSkinManager.isWalletConnected()) {
            font.color = Color.GRAY
            font.draw(batch, "You don't own any skins for this class yet.", 60f, y)
            y -= 30f
            font.draw(batch, "Check the Marketplace tab to buy some!", 60f, y)
        } else if (!NFTSkinManager.isWalletConnected()) {
            font.color = Color.YELLOW
            font.draw(batch, "Connect your wallet to see your NFT skins!", 60f, y)
            y -= 30f
            font.color = Color.GRAY
            font.draw(batch, "Press [W] to connect wallet", 60f, y)
        }

        ownedSkins.forEachIndexed { index, skin ->
            val listIndex = index + 1 // +1 because default is index 0
            val isActive = NFTSkinManager.getActiveSkin(heroClass) == skin
            val isSelected = selectedIndex == listIndex && selectedTab == Tab.OWNED

            font.color = getRarityColor(skin.rarity)
            if (isSelected) font.color = Color.YELLOW

            val skinPrefix = if (isSelected) "> " else "  "
            val equippedText = if (isActive) " [EQUIPPED]" else ""
            font.draw(batch, "$skinPrefix${skin.name}$equippedText", 60f, y)

            font.color = Color.GRAY
            font.data.setScale(1f)
            font.draw(batch, "${skin.rarity.displayName} - NFT #${skin.tokenId}", 80f, y - 20)
            font.data.setScale(1.2f)
            y -= 50f
        }
    }

    private fun renderMarketplace(startY: Float) {
        var y = startY

        if (!NFTSkinManager.isWalletConnected()) {
            font.color = Color.YELLOW
            font.data.setScale(1.5f)
            font.draw(batch, "Connect Wallet to Access Marketplace", 60f, y)
            font.data.setScale(1.2f)
            y -= 40f

            font.color = Color.GRAY
            font.draw(batch, "Press [W] to connect your web3 wallet", 60f, y)
            y -= 30f
            font.draw(batch, "Buy, sell, and trade cosmetic NFT skins!", 60f, y)
            return
        }

        val listings = NFTSkinManager.getMarketplaceSkins(heroClass)

        if (listings.isEmpty()) {
            font.color = Color.GRAY
            font.draw(batch, "No skins available in marketplace right now.", 60f, y)
            y -= 30f
            font.draw(batch, "Check back later for new drops!", 60f, y)
            return
        }

        listings.forEachIndexed { index, listing ->
            val isSelected = selectedIndex == index && selectedTab == Tab.MARKETPLACE

            font.color = getRarityColor(listing.skin.rarity)
            if (isSelected) font.color = Color.YELLOW

            val prefix = if (isSelected) "> " else "  "
            font.draw(batch, "$prefix${listing.skin.name}", 60f, y)

            font.color = Color.GRAY
            font.data.setScale(1f)
            font.draw(batch, "${listing.skin.rarity.displayName} - ${listing.price} ETH", 80f, y - 20)
            font.data.setScale(1.2f)
            y -= 50f
        }
    }

    private fun getRarityColor(rarity: Rarity): Color {
        return when (rarity) {
            Rarity.COMMON -> Color.WHITE
            Rarity.RARE -> Color.BLUE
            Rarity.EPIC -> Color.PURPLE
            Rarity.LEGENDARY -> Color.ORANGE
        }
    }

    private fun handleInput() {
        // Tab switching
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            selectedTab = Tab.OWNED
            selectedIndex = 0
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            selectedTab = Tab.MARKETPLACE
            selectedIndex = 0
        }

        // Navigation
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            val maxIndex = when (selectedTab) {
                Tab.OWNED -> NFTSkinManager.getOwnedSkins(heroClass).size // +1 for default, but 0-indexed
                Tab.MARKETPLACE -> NFTSkinManager.getMarketplaceSkins(heroClass).size - 1
            }
            selectedIndex = (selectedIndex - 1).coerceAtLeast(0)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            val maxIndex = when (selectedTab) {
                Tab.OWNED -> NFTSkinManager.getOwnedSkins(heroClass).size
                Tab.MARKETPLACE -> NFTSkinManager.getMarketplaceSkins(heroClass).size - 1
            }
            selectedIndex = (selectedIndex + 1).coerceAtMost(maxIndex)
        }

        // Actions
        if (selectedTab == Tab.OWNED) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                if (selectedIndex == 0) {
                    // Equip default skin (unequip NFT)
                    NFTSkinManager.unequipSkin(heroClass)
                    Gdx.app.log("Skins", "Equipped default ${heroClass.title} skin")
                } else {
                    val skin = NFTSkinManager.getOwnedSkins(heroClass).getOrNull(selectedIndex - 1)
                    if (skin != null && NFTSkinManager.equipSkin(skin)) {
                        Gdx.app.log("Skins", "Equipped ${skin.name}")
                    }
                }
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
                NFTSkinManager.unequipSkin(heroClass)
                Gdx.app.log("Skins", "Unequipped skin, using default")
            }
        }

        if (selectedTab == Tab.MARKETPLACE) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
                // TODO: Implement purchase
                Gdx.app.log("Skins", "Purchase not yet implemented - integrate with your marketplace")
            }
        }

        // Wallet connection
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && !NFTSkinManager.isWalletConnected()) {
            // TODO: Integrate with your web3 wallet connection
            // For now, mock connection
            NFTSkinManager.connectWallet("0x1234567890abcdef1234567890abcdef12345678")
            Gdx.app.log("Skins", "Wallet connected (mock)")
        }

        // Back
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.screen = ClassSelectScene(game)
        }
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
    }
}
