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
import com.hardcoredungeon.engine.Dungeon
import com.hardcoredungeon.levels.Level

/**
 * Main game scene where gameplay happens
 */
class GameScene(game: HardcoreDungeon, heroClass: HeroClass) : BaseScene(game) {

    private val camera = OrthographicCamera()
    private val batch = SpriteBatch()
    private val shapeRenderer = ShapeRenderer()
    private val font = BitmapFont()

    private val dungeon: Dungeon
    private val hero: Hero
    private val level: Level

    init {
        // Initialize camera
        camera.setToOrtho(false,
            HardcoreDungeon.VIEWPORT_WIDTH * HardcoreDungeon.TILE_SIZE.toFloat(),
            HardcoreDungeon.VIEWPORT_HEIGHT * HardcoreDungeon.TILE_SIZE.toFloat())

        // Create hero
        hero = Hero(heroClass)

        // Initialize dungeon
        dungeon = Dungeon(hero)
        level = dungeon.currentLevel

        // Place hero at entrance
        hero.pos = level.entrance

        font.color = Color.WHITE

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

        // Render tiles
        for (y in 0 until level.height) {
            for (x in 0 until level.width) {
                val tile = level.getTile(x, y)
                val color = when {
                    tile.solid -> Color.DARK_GRAY
                    tile.water -> Color.BLUE
                    else -> Color.LIGHT_GRAY
                }

                shapeRenderer.color = color
                shapeRenderer.rect(x * tileSize, y * tileSize, tileSize, tileSize)
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

        // Stats
        font.draw(batch, "HP: ${hero.hp}/${hero.maxHP}", 10f, Gdx.graphics.height - 10f)
        font.draw(batch, "Depth: ${dungeon.depth}", 10f, Gdx.graphics.height - 30f)
        font.draw(batch, "Hunger: ${hero.hunger}/1000", 10f, Gdx.graphics.height - 50f)
        font.draw(batch, "Gold: ${hero.gold}", 10f, Gdx.graphics.height - 70f)

        batch.end()
    }

    private fun handleInput() {
        var moved = false

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            moved = hero.move(0, 1, level)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            moved = hero.move(0, -1, level)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            moved = hero.move(-1, 0, level)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            moved = hero.move(1, 0, level)
        }

        if (moved) {
            // Apply hunger on each move
            hero.hunger -= 1
            if (hero.hunger <= 0) {
                hero.hp -= 1  // Starvation damage
                if (hero.hp <= 0) {
                    Gdx.app.log("GameScene", "Hero died from starvation!")
                    game.screen = TitleScene(game)
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.screen = TitleScene(game)
        }
    }

    override fun dispose() {
        batch.dispose()
        shapeRenderer.dispose()
        font.dispose()
    }
}
