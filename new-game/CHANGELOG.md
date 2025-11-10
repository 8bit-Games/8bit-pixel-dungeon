# Changelog

All notable changes to Hardcore Dungeon will be documented in this file.

## [Unreleased - Fully Playable] - 2025-11-09

### 🎮 Major Features Added
- **Complete gameplay loop** - Game is now fully playable from start to death!
- **Turn-based combat system** - Fight enemies with damage calculation
- **Mob spawning** - 8-15 enemies per level with depth-appropriate variety
- **Item spawning** - 3-8 items per level randomly placed
- **Food system** - 4 food types to manage hunger
- **Loot drops** - 30% chance for items when killing enemies
- **Level progression** - Working stairs to descend through infinite depths
- **Message log** - Color-coded feedback for all player actions

### 🎨 UI Improvements
- **Inventory screen** - Full-featured UI for managing 20 items
  - Equipment management (weapon, armor, 2 rings)
  - Use consumables (potions, scrolls, food)
  - Drop items
  - Visual indicators for equipped items
- **Enhanced HUD** - Color-coded stats (red for low HP/hunger)
- **Message log** - Last 5 messages displayed with color coding
- **Visual indicators** - Items (pink/magenta circles), enemies (red squares)
- **Control hints** - Always visible at bottom of screen

### 🏭 Factories & Systems
- **MobFactory** - Depth-based mob spawning across 5 dungeon zones
  - Sewers (1-5): Rats, Crabs, Gnolls, Bats
  - Prison (6-10): Gnolls, Skeletons, Thieves, Bats
  - Caves (11-15): Skeletons, Bats, Snakes, Golems
  - City (16-20): Snakes, Golems, Skeletons, Thieves
  - Halls (21+): Golems, Skeletons, Demon Lords
- **ItemFactory** - Random item generation based on depth
  - Early levels: Basic weapons and armor
  - Deep levels: Advanced equipment
  - Consumables spawn at all depths
- **GameLog** - Centralized message system with color coding

### 🍖 New Items
- **Food items** - Ration (400), Bread (200), Meat (300), Fruit (150)
- All consumables now functional

### 🎯 Controls Added
- **G** - Pick up items
- **E** - Eat food
- **I** - Open inventory
- **>** (Shift+.) - Use stairs
- **Inventory controls** - E to equip/use, D to drop

### 🧪 Testing
- **PointTest** - Tests 2D coordinate math, distance calculations
- **HeroTest** - Tests character creation, leveling, damage system
- **LevelTest** - Tests dungeon generation, mob/item spawning
- **InventoryTest** - Tests item management, equipment, stacking
- **JUnit 4.13.2** added as test dependency

### 🔧 Technical Improvements
- Enhanced Level class with mob/item management
- Smart spawning prevents overlap and entrance blocking
- Refactored GameScene for complete gameplay
- Added test directory structure
- Comprehensive unit test coverage

### 📊 Statistics
- **34 Kotlin files** (up from 25)
- **2,574 lines of code** (up from 1,561)
- **4 test suites** with 20+ unit tests
- **7 actor files** (hero, mobs, buffs)
- **6 item files** (weapons, armor, consumables)
- **5 engine files** (dungeon, FOV, save, log, point)
- **5 scene files** (title, class select, game, inventory, base)

### 🐛 Bug Fixes
- Fixed typo in MobFactory.createSewersMob()
- Corrected import for ItemFactory

---

## [0.1.0 - Initial Foundation] - 2025-11-08

### ✨ Initial Release
- Project setup with LibGDX + Kotlin
- Cross-platform support (Android/Desktop/iOS)
- 4 character classes
- Procedural dungeon generation
- Basic movement and rendering
- Hunger system
- Item system foundation
- Combat system foundation
- Status effects system
- Field of View algorithm
- Save/load system
- MIT License

---

## Future Roadmap

### Phase 1: Content Expansion
- [ ] More enemy types (20+ total)
- [ ] More items (50+ total)
- [ ] Boss fights on every 5th level
- [ ] Special rooms (shops, treasure, altars)
- [ ] Wands and magic system
- [ ] More status effects

### Phase 2: Polish & Assets
- [ ] Pixel art tileset
- [ ] Character sprites
- [ ] Enemy sprites
- [ ] Item sprites
- [ ] Sound effects
- [ ] Background music
- [ ] Better UI with pixel art fonts

### Phase 3: Features
- [ ] Achievements system
- [ ] High score tracking
- [ ] Daily challenges
- [ ] Multiple difficulty modes
- [ ] Item enchantments
- [ ] Thrown weapons

### Phase 4: Release
- [ ] App store graphics
- [ ] Marketing materials
- [ ] Beta testing
- [ ] Google Play release
- [ ] Steam release (optional)
