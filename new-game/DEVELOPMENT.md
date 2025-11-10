# Development Guide

## Project Overview

Hardcore Dungeon is a from-scratch roguelike built with:
- **Language:** Kotlin
- **Framework:** LibGDX 1.12.1
- **Build Tool:** Gradle 8.5
- **Target Platforms:** Android, Desktop (Windows/Mac/Linux), iOS

## Architecture

### Package Structure

```
com.hardcoredungeon/
├── engine/          # Core game systems
│   ├── Dungeon.kt          # Dungeon state manager
│   ├── Point.kt            # 2D point utilities
│   ├── FieldOfView.kt      # FOV calculations
│   └── GameSave.kt         # Save/load system
│
├── actors/          # Game entities
│   ├── Actor.kt            # Base entity class
│   ├── hero/               # Player character
│   │   ├── Hero.kt         # Player implementation
│   │   ├── HeroClass.kt    # Character classes
│   │   └── Inventory.kt    # Inventory system
│   ├── mobs/               # Enemies
│   │   ├── Mob.kt          # Base mob class
│   │   └── MobTypes.kt     # Specific enemies
│   └── buffs/              # Status effects
│       └── Buff.kt         # Buff/debuff system
│
├── items/           # Item system
│   ├── Item.kt             # Base item class
│   ├── weapons/            # Melee & ranged weapons
│   ├── armor/              # Armor pieces
│   └── consumables/        # Potions & scrolls
│
├── levels/          # Dungeon generation
│   ├── Level.kt            # Level class
│   └── Tile.kt             # Tile types
│
├── scenes/          # Game screens
│   ├── BaseScene.kt        # Screen base class
│   ├── TitleScene.kt       # Main menu
│   ├── ClassSelectScene.kt # Class selection
│   └── GameScene.kt        # Main gameplay
│
└── HardcoreDungeon.kt      # Main game class
```

## Core Systems

### 1. Turn-Based System
- Each action takes 1 turn
- Hero moves → Mobs act → Repeat
- Hunger decreases each turn

### 2. Combat System
```kotlin
damage = max(1, attack - defense)
critical = damage * 1.5  // For rogues
```

### 3. Hunger System
- Starts at 1000
- Decreases 1 per turn
- At 0: Take 1 damage per turn (starvation)

### 4. Leveling System
```kotlin
requiredExp = currentLevel * 10
onLevelUp:
  maxHP += 5
  strength += 1
  attack += 1
```

### 5. Dungeon Generation
- Room-based algorithm
- 15 rooms per level
- L-shaped corridors
- Entrance → Exit placement

### 6. Field of View
- Shadow casting algorithm
- 6-tile vision radius
- Line of sight blocking

## Adding New Features

### New Mob Type
```kotlin
class NewMob : Mob(
    name = "New Mob",
    baseHP = 20,
    attack = 5,
    defense = 2,
    expValue = 15
)
```

### New Item
```kotlin
class NewPotion : Potion("New Potion") {
    override fun use(hero: Hero): Boolean {
        // Your effect here
        return super.use(hero)
    }
}
```

### New Character Class
```kotlin
HeroClass(
    title = "New Class",
    description = "Description",
    baseHP = 18,
    baseStr = 10,
    baseAttack = 3,
    baseDefense = 1
)
```

## Building

### Desktop Development
```bash
./gradlew desktop:run
```

### Android APK
```bash
./gradlew android:assembleDebug
# Output: android/build/outputs/apk/debug/android-debug.apk
```

### Production Build
```bash
./gradlew android:assembleRelease
# Don't forget to sign the APK!
```

## Testing

Currently no automated tests (traditional roguelike development style).

### Manual Testing Checklist
- [ ] All 4 classes playable
- [ ] Movement works (WASD + arrows)
- [ ] Combat deals damage
- [ ] Hunger decreases
- [ ] Death triggers game over
- [ ] Items can be picked up
- [ ] Equipment affects stats
- [ ] Potions work
- [ ] Level generation creates playable levels
- [ ] Mobs AI works (wander/hunt/attack)

## Performance

### Optimization Tips
1. Pool game objects (items, mobs)
2. Batch sprite rendering
3. Cache FOV calculations
4. Lazy load levels
5. Compress save files

### Target Performance
- 60 FPS on desktop
- 30+ FPS on Android (mid-range)
- < 50MB RAM usage
- < 10MB APK size (before assets)

## Asset Pipeline

### Sprites (TODO)
- 16x16 pixel art
- Separate atlas for tiles, mobs, items, UI
- Use LibGDX TexturePacker

### Sounds (TODO)
- .ogg format for cross-platform
- Short SFX < 1s
- Music looping

### Fonts
- Currently using LibGDX default
- TODO: Pixel font (e.g., Press Start 2P)

## Release Checklist

- [ ] Update version in build.gradle.kts
- [ ] Test on multiple Android devices
- [ ] Test on Windows/Mac/Linux
- [ ] Create app icons (all sizes)
- [ ] Write store description
- [ ] Take screenshots
- [ ] ProGuard for Android release
- [ ] Sign APK with release key
- [ ] Test release build

## Known Limitations

1. **No networking** - Single player only
2. **No cloud saves** - Local only
3. **No analytics** - Add if needed
4. **No IAP** - Can be added later
5. **No ads** - Clean game

## Future Enhancements

### High Priority
- [ ] Pixel art tileset
- [ ] Sound effects
- [ ] Background music
- [ ] Better UI (pixel art style)
- [ ] More mob types (50+)
- [ ] Boss fights
- [ ] Special rooms

### Medium Priority
- [ ] Achievements
- [ ] Daily challenges
- [ ] Leaderboards
- [ ] Item enchantments
- [ ] Wands/magic system
- [ ] Thrown weapons

### Low Priority
- [ ] Multiplayer
- [ ] Mod support
- [ ] Level editor
- [ ] Speedrun mode

## Contributing

This is a solo project but follows these principles:
- **KISS** - Keep It Simple, Stupid
- **YAGNI** - You Aren't Gonna Need It
- **DRY** - Don't Repeat Yourself
- Code in Kotlin, comment for clarity
- Every class has a single responsibility

## Legal

**100% Original Code** - No GPL dependencies
- Safe for commercial use
- MIT Licensed
- Sell on any platform (Google Play, Steam, itch.io, etc.)
- No attribution required
