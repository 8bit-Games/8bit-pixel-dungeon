package com.hardcoredungeon.nft

import com.hardcoredungeon.actors.hero.HeroClass

/**
 * NFT Skin Manager - Integrates with web3 wallet for cosmetic skins
 *
 * This allows players to:
 * - Buy/trade cosmetic character skins as NFTs
 * - Unlock premium classes via NFT ownership
 * - Earn achievement badge NFTs
 * - Display owned skins in-game
 *
 * Game works perfectly fine without NFTs - this is optional premium content!
 */
object NFTSkinManager {

    private var connectedWallet: String? = null
    private val ownedSkins = mutableListOf<CharacterSkin>()
    private val activeSkins = mutableMapOf<HeroClass, CharacterSkin>()

    /**
     * Connect web3 wallet and load owned NFT skins
     */
    fun connectWallet(address: String) {
        connectedWallet = address
        loadOwnedSkins(address)
    }

    fun disconnectWallet() {
        connectedWallet = null
        ownedSkins.clear()
        activeSkins.clear()
    }

    fun isWalletConnected(): Boolean = connectedWallet != null

    fun getWalletAddress(): String? = connectedWallet

    /**
     * Query blockchain for owned skin NFTs
     * TODO: Integrate with your web3 provider
     */
    private fun loadOwnedSkins(address: String) {
        ownedSkins.clear()

        // TODO: Replace with actual blockchain query
        // Example:
        // val contract = web3Provider.getContract("HardcoreDungeonSkins")
        // val tokens = contract.tokensOfOwner(address)
        // tokens.forEach { tokenId ->
        //     val metadata = contract.tokenURI(tokenId)
        //     val skin = parseMetadata(metadata)
        //     ownedSkins.add(skin)
        // }

        // For now, mock data for testing:
        if (address.isNotEmpty()) {
            // Mock: User owns a rare Warrior skin
            ownedSkins.add(
                CharacterSkin(
                    id = 1,
                    name = "Viking Warrior",
                    heroClass = HeroClass.WARRIOR,
                    rarity = Rarity.RARE,
                    spriteSheet = "skins/warrior_viking.png",
                    contractAddress = "0x...",
                    tokenId = 123
                )
            )
        }
    }

    /**
     * Get all skins owned by connected wallet
     */
    fun getOwnedSkins(heroClass: HeroClass? = null): List<CharacterSkin> {
        return if (heroClass != null) {
            ownedSkins.filter { it.heroClass == heroClass }
        } else {
            ownedSkins.toList()
        }
    }

    /**
     * Get currently equipped skin for a class (or null for default)
     */
    fun getActiveSkin(heroClass: HeroClass): CharacterSkin? {
        return activeSkins[heroClass]
    }

    /**
     * Equip a skin for a class
     */
    fun equipSkin(skin: CharacterSkin): Boolean {
        if (!ownedSkins.contains(skin)) {
            return false // Don't own this skin
        }

        activeSkins[skin.heroClass] = skin
        return true
    }

    /**
     * Unequip skin (return to default)
     */
    fun unequipSkin(heroClass: HeroClass) {
        activeSkins.remove(heroClass)
    }

    /**
     * Check if wallet owns a specific skin
     */
    fun ownsSkin(skinId: Int): Boolean {
        return ownedSkins.any { it.id == skinId }
    }

    /**
     * Get marketplace skins available for purchase
     * TODO: Query your NFT marketplace contract
     */
    fun getMarketplaceSkins(heroClass: HeroClass): List<MarketplaceListing> {
        // TODO: Replace with actual marketplace query
        // Example:
        // val marketplace = web3Provider.getContract("HardcoreDungeonMarketplace")
        // return marketplace.getListings(heroClass.name)

        // Mock data for testing:
        return listOf(
            MarketplaceListing(
                skin = CharacterSkin(
                    id = 2,
                    name = "Knight Warrior",
                    heroClass = HeroClass.WARRIOR,
                    rarity = Rarity.COMMON,
                    spriteSheet = "skins/warrior_knight.png",
                    contractAddress = "0x...",
                    tokenId = 0 // Not minted yet
                ),
                price = "0.001", // ETH or your token
                seller = "marketplace",
                listingId = 1
            )
        )
    }

    /**
     * Purchase skin from marketplace
     * TODO: Integrate with your marketplace contract
     */
    suspend fun purchaseSkin(listingId: Int): Boolean {
        // TODO: Implement marketplace purchase
        // Example:
        // val marketplace = web3Provider.getContract("HardcoreDungeonMarketplace")
        // val tx = marketplace.purchase(listingId)
        // return tx.wait()

        return false // Placeholder
    }

    /**
     * Mint achievement badge NFT
     * Called when player unlocks rare achievement
     */
    suspend fun mintAchievementBadge(achievementId: String): Boolean {
        if (connectedWallet == null) return false

        // TODO: Integrate with achievement badge contract
        // Example:
        // val badgeContract = web3Provider.getContract("AchievementBadges")
        // val tx = badgeContract.mint(connectedWallet, achievementId)
        // return tx.wait()

        return false // Placeholder
    }
}

/**
 * Character skin NFT data
 */
data class CharacterSkin(
    val id: Int,
    val name: String,
    val heroClass: HeroClass,
    val rarity: Rarity,
    val spriteSheet: String, // Path to sprite asset
    val contractAddress: String, // NFT contract address
    val tokenId: Int // NFT token ID (0 if not minted yet)
)

/**
 * Rarity tiers for skins
 */
enum class Rarity(val displayName: String, val color: String) {
    COMMON("Common", "#FFFFFF"),
    RARE("Rare", "#0070DD"),
    EPIC("Epic", "#A335EE"),
    LEGENDARY("Legendary", "#FF8000");

    fun getDropChance(): Float {
        return when (this) {
            COMMON -> 0.60f      // 60%
            RARE -> 0.25f        // 25%
            EPIC -> 0.12f        // 12%
            LEGENDARY -> 0.03f   // 3%
        }
    }
}

/**
 * Marketplace listing for skins
 */
data class MarketplaceListing(
    val skin: CharacterSkin,
    val price: String, // In ETH or your token
    val seller: String, // Wallet address or "marketplace"
    val listingId: Int
)

/**
 * Achievement badge NFT
 */
data class AchievementBadge(
    val achievementId: String,
    val name: String,
    val description: String,
    val rarity: Rarity,
    val imageURI: String, // IPFS link
    val mintedDate: Long,
    val totalMinted: Int, // For rarity tracking
    val tokenId: Int
)
