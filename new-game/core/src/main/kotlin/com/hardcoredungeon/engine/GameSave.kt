package com.hardcoredungeon.engine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.hardcoredungeon.actors.hero.Hero
import com.hardcoredungeon.actors.hero.HeroClass

/**
 * Save/Load system with permadeath
 */
object GameSave {

    private const val SAVE_FILE = "save.dat"

    data class SaveData(
        val heroClass: HeroClass,
        val depth: Int,
        val hp: Int,
        val maxHP: Int,
        val strength: Int,
        val attack: Int,
        val defense: Int,
        val gold: Int,
        val hunger: Int,
        val experience: Int,
        val level: Int,
        val seed: Long
    )

    fun save(dungeon: Dungeon) {
        val hero = dungeon.hero
        val data = SaveData(
            heroClass = hero.heroClass,
            depth = dungeon.depth,
            hp = hero.hp,
            maxHP = hero.maxHP,
            strength = hero.strength,
            attack = hero.attack,
            defense = hero.defense,
            gold = hero.gold,
            hunger = hero.hunger,
            experience = hero.experience,
            level = hero.level,
            seed = System.currentTimeMillis()
        )

        try {
            val file: FileHandle = Gdx.files.local(SAVE_FILE)
            val json = serializeToJson(data)
            file.writeString(json, false)
            Gdx.app.log("GameSave", "Game saved successfully")
        } catch (e: Exception) {
            Gdx.app.error("GameSave", "Failed to save game", e)
        }
    }

    fun load(): SaveData? {
        try {
            val file: FileHandle = Gdx.files.local(SAVE_FILE)
            if (!file.exists()) {
                return null
            }

            val json = file.readString()
            return deserializeFromJson(json)
        } catch (e: Exception) {
            Gdx.app.error("GameSave", "Failed to load game", e)
            return null
        }
    }

    fun deleteSave() {
        try {
            val file: FileHandle = Gdx.files.local(SAVE_FILE)
            if (file.exists()) {
                file.delete()
                Gdx.app.log("GameSave", "Save deleted (permadeath)")
            }
        } catch (e: Exception) {
            Gdx.app.error("GameSave", "Failed to delete save", e)
        }
    }

    fun hasSave(): Boolean {
        return Gdx.files.local(SAVE_FILE).exists()
    }

    private fun serializeToJson(data: SaveData): String {
        // Simple JSON serialization
        return """
        {
            "heroClass": "${data.heroClass.name}",
            "depth": ${data.depth},
            "hp": ${data.hp},
            "maxHP": ${data.maxHP},
            "strength": ${data.strength},
            "attack": ${data.attack},
            "defense": ${data.defense},
            "gold": ${data.gold},
            "hunger": ${data.hunger},
            "experience": ${data.experience},
            "level": ${data.level},
            "seed": ${data.seed}
        }
        """.trimIndent()
    }

    private fun deserializeFromJson(json: String): SaveData {
        // Simple JSON deserialization
        val values = mutableMapOf<String, String>()

        json.lines().forEach { line ->
            val trimmed = line.trim().removeSuffix(",")
            if (trimmed.contains(":")) {
                val parts = trimmed.split(":")
                val key = parts[0].trim().removeSurrounding("\"")
                val value = parts[1].trim().removeSurrounding("\"")
                values[key] = value
            }
        }

        return SaveData(
            heroClass = HeroClass.valueOf(values["heroClass"]!!),
            depth = values["depth"]!!.toInt(),
            hp = values["hp"]!!.toInt(),
            maxHP = values["maxHP"]!!.toInt(),
            strength = values["strength"]!!.toInt(),
            attack = values["attack"]!!.toInt(),
            defense = values["defense"]!!.toInt(),
            gold = values["gold"]!!.toInt(),
            hunger = values["hunger"]!!.toInt(),
            experience = values["experience"]!!.toInt(),
            level = values["level"]!!.toInt(),
            seed = values["seed"]!!.toLong()
        )
    }
}
