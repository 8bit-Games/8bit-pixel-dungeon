package com.hardcoredungeon.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.hardcoredungeon.HardcoreDungeon
import com.hardcoredungeon.actors.hero.Hero
import com.hardcoredungeon.actors.hero.HeroClass
import com.hardcoredungeon.actors.mobs.Mob
import com.hardcoredungeon.engine.Dungeon
import com.hardcoredungeon.engine.GameLog
import com.hardcoredungeon.items.Item
import com.hardcoredungeon.items.consumables.Food
import com.hardcoredungeon.levels.Level
import com.hardcoredungeon.levels.TileType
import kotlin.math.max

/**
 * Main game scene where gameplay happens
 */
class GameScene(
    game: HardcoreDungeon,
    heroClass: HeroClass,
    private val isDaily: Boolean = false,
    private val seed: Long = System.currentTimeMillis()
) : BaseScene(game) {

    private val camera = OrthographicCamera()
    private val batch = SpriteBatch()
    private val shapeRenderer = ShapeRenderer()
    private val font = BitmapFont()

    private val dungeon: Dungeon
    private val hero: Hero
    private var level: Level

    init {
        // Initialize camera
        camera.setToOrtho(false,
            HardcoreDungeon.VIEWPORT_WIDTH * HardcoreDungeon.TILE_SIZE.toFloat(),
            HardcoreDungeon.VIEWPORT_HEIGHT * HardcoreDungeon.TILE_SIZE.toFloat())

        // Create hero
        hero = Hero(heroClass)

        // Initialize dungeon with seed
        dungeon = Dungeon(hero, seed)
        level = dungeon.currentLevel

        // Place hero at entrance
        hero.pos.set(level.entrance)

        font.color = Color.WHITE

        GameLog.clear()
        GameLog.positive("Welcome to Hardcore Dungeon!")
        GameLog.info("You are a ${heroClass.title}. Good luck!")

        if (isDaily) {
            GameLog.info("Daily Challenge Mode - Good luck!")
        }

        // Start tracking stats
        com.hardcoredungeon.engine.GameStats.startRun()

        Gdx.app.log("GameScene", "Started game with ${heroClass.title}")
    }

    override fun render(delta: Float) {
        camera.update()
        batch.projectionMatrix = camera.combined
        shapeRenderer.projectionMatrix = camera.combined

        // Render level
        renderLevel()

        // Render UI
        renderUI()

        // Handle input
        handleInput()
    }

    private fun renderLevel() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        val tileSize = HardcoreDungeon.TILE_SIZE.toFloat()

        // Render tiles (only visible area for performance)
        val startX = max(0, hero.pos.x - 10)
        val endX = kotlin.math.min(level.width, hero.pos.x + 11)
        val startY = max(0, hero.pos.y - 10)
        val endY = kotlin.math.min(level.height, hero.pos.y + 11)

        for (y in startY until endY) {
            for (x in startX until endX) {
                val tile = level.getTile(x, y)
                val color = when (tile.type) {
                    TileType.WALL -> Color.DARK_GRAY
                    TileType.WATER -> Color.BLUE
                    TileType.ENTRANCE -> Color.GREEN
                    TileType.EXIT -> Color.CYAN
                    TileType.DOOR -> Color.BROWN
                    TileType.CHASM -> Color.BLACK
                    else -> Color.LIGHT_GRAY
                }

                shapeRenderer.color = color
                shapeRenderer.rect(x * tileSize, y * tileSize, tileSize, tileSize)
            }
        }

        // Render items
        for (item in level.items) {
            if (item.pos.x in startX until endX && item.pos.y in startY until endY) {
                shapeRenderer.color = when (item) {
                    is Food -> Color.PINK
                    else -> Color.MAGENTA
                }
                shapeRenderer.circle(
                    item.pos.x * tileSize + tileSize / 2,
                    item.pos.y * tileSize + tileSize / 2,
                    tileSize / 4
                )
            }
        }

        // Render mobs
        for (mob in level.mobs) {
            if (mob.isAlive && mob.pos.x in startX until endX && mob.pos.y in startY until endY) {
                shapeRenderer.color = Color.RED
                shapeRenderer.rect(
                    mob.pos.x * tileSize + 2,
                    mob.pos.y * tileSize + 2,
                    tileSize - 4,
                    tileSize - 4
                )
            }
        }

        // Render hero
        shapeRenderer.color = Color.YELLOW
        shapeRenderer.rect(
            hero.pos.x * tileSize + 2,
            hero.pos.y * tileSize + 2,
            tileSize - 4,
            tileSize - 4
        )

        shapeRenderer.end()
    }

    private fun renderUI() {
        batch.begin()

        val screenWidth = Gdx.graphics.width.toFloat()
        val screenHeight = Gdx.graphics.height.toFloat()

        // Top bar - stats
        font.color = if (hero.hp < hero.maxHP / 4) Color.RED else Color.WHITE
        font.draw(batch, "HP: ${hero.hp}/${hero.maxHP}", 10f, screenHeight - 10f)

        font.color = Color.WHITE
        font.draw(batch, "Depth: ${dungeon.depth}", 10f, screenHeight - 30f)

        font.color = if (hero.hunger < 200) Color.RED else if (hero.hunger < 400) Color.YELLOW else Color.WHITE
        font.draw(batch, "Hunger: ${hero.hunger}/1000", 10f, screenHeight - 50f)

        font.draw(batch, "Gold: ${hero.gold}", 10f, screenHeight - 70f)
        font.draw(batch, "Level: ${hero.level}", 10f, screenHeight - 90f)

        // Message log
        val messages = GameLog.getRecent(5)
        var y = screenHeight - 120f
        for (msg in messages.reversed()) {
            font.color = msg.color
            font.draw(batch, msg.text, 10f, y)
            y -= 20f
        }

        // Controls hint
        font.color = Color.GRAY
        font.draw(batch, "[WASD] Move  [G] Get Item  [I] Inventory  [E] Eat  [>] Stairs", 10f, 20f)

        batch.end()
    }

    private fun handleInput() {
        // Movement
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            tryMove(0, 1)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            tryMove(0, -1)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            tryMove(-1, 0)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            tryMove(1, 0)
        }

        // Get item
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            pickupItem()
        }

        // Eat food
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            eatFood()
        }

        // Use stairs
        if (Gdx.input.isKeyJustPressed(Input.Keys.PERIOD) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            useStairs()
        }

        // Inventory
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            game.screen = InventoryScene(game, hero, this)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.screen = TitleScene(game)
        }
    }

    private fun tryMove(dx: Int, dy: Int) {
        val newX = hero.pos.x + dx
        val newY = hero.pos.y + dy

        // Check for mob
        val mob = level.getMobAt(newX, newY)
        if (mob != null && mob.isAlive) {
            attackMob(mob)
            endPlayerTurn()
            return
        }

        // Try to move
        if (hero.move(dx, dy, level)) {
            endPlayerTurn()
        }
    }

    private fun attackMob(mob: Mob) {
        val damage = max(1, hero.attack - mob.defense)
        mob.takeDamage(damage)

        GameLog.combat("You hit ${mob.name} for $damage damage!")

        if (!mob.isAlive) {
            GameLog.positive("You defeated ${mob.name}!")
            hero.gainExp(mob.expValue)

            val goldEarned = (1..5).random()
            hero.gold += goldEarned

            // Track stats
            com.hardcoredungeon.engine.GameStats.recordKill(hero.heroClass, mob.name.contains("Boss") || mob.name.contains("Demon"))
            com.hardcoredungeon.engine.GameStats.recordGold(goldEarned)

            // Drop loot
            if ((0..100).random() < 30) {
                val item = com.hardcoredungeon.levels.ItemFactory.createRandomItem(dungeon.depth)
                item.pos.set(mob.pos)
                level.items.add(item)
            }
        }
    }

    private fun pickupItem() {
        val item = level.getItemAt(hero.pos.x, hero.pos.y)
        if (item != null) {
            if (hero.inventory.add(item)) {
                level.items.remove(item)
                GameLog.positive("Picked up ${item.name}")

                // Track stats
                com.hardcoredungeon.engine.GameStats.recordItemCollected()

                // Check inventory achievement
                if (hero.inventory.isFull()) {
                    com.hardcoredungeon.engine.Achievements.unlock(
                        com.hardcoredungeon.engine.Achievements.Achievement.FULL_INVENTORY
                    )
                }
            } else {
                GameLog.warning("Inventory full!")
            }
        } else {
            GameLog.info("Nothing to pick up here.")
        }
    }

    private fun eatFood() {
        val food = hero.inventory.getItems().find { it is Food } as? Food
        if (food != null) {
            food.use(hero)
            if (food.quantity <= 0) {
                hero.inventory.remove(food)
            }
            GameLog.positive("You eat ${food.name}. Hunger restored!")

            // Track stats
            com.hardcoredungeon.engine.GameStats.recordFoodEaten()
        } else {
            GameLog.warning("You have no food!")
        }
    }

    private fun useStairs() {
        val tile = level.getTile(hero.pos.x, hero.pos.y)
        if (tile.type == TileType.EXIT) {
            GameLog.positive("You descend to depth ${dungeon.depth + 1}...")
            dungeon.descendLevel()
            level = dungeon.currentLevel
            hero.pos.set(level.entrance)
        } else {
            GameLog.info("There are no stairs here.")
        }
    }

    private fun endPlayerTurn() {
        // Track turn
        com.hardcoredungeon.engine.GameStats.recordTurn()

        // Apply hunger
        hero.hunger -= 1
        if (hero.hunger <= 0) {
            hero.hp -= 1
            GameLog.error("You are starving!")
            if (hero.hp <= 0) {
                // Achievement for starving
                com.hardcoredungeon.engine.Achievements.unlock(
                    com.hardcoredungeon.engine.Achievements.Achievement.STARVE
                )
                gameOver("You starved to death!")
                return
            }
        }

        // Mobs take turns
        for (mob in level.mobs) {
            if (mob.isAlive) {
                mob.actTurn(hero, level)
            }
        }

        // Check if hero died
        if (!hero.isAlive) {
            gameOver("You were killed!")
        }

        // Check depth achievements
        com.hardcoredungeon.engine.Achievements.checkDepthAchievement(dungeon.depth)
    }

    private fun gameOver(message: String) {
        GameLog.error(message)
        GameLog.error("Game Over!")
        Gdx.app.log("GameScene", message)

        // End run and record stats
        com.hardcoredungeon.engine.GameStats.endRun(dungeon.depth, hero.heroClass, won = false)

        // Record daily challenge score if applicable
        if (isDaily) {
            val score = com.hardcoredungeon.engine.Challenge.calculateScore(dungeon.depth, hero.gold)
            com.hardcoredungeon.engine.DailyChallenge.recordScore(dungeon.depth, score)
        }

        // Small delay then return to title
        Thread.sleep(1000)
        game.screen = TitleScene(game)
    }

    override fun dispose() {
        batch.dispose()
        shapeRenderer.dispose()
        font.dispose()
    }
}
