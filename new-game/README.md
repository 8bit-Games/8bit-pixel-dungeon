# Hardcore Dungeon

A brutal roguelike dungeon crawler built from scratch with Kotlin and LibGDX.

## 🎮 Features

### Hardcore Survival
- **Permadeath** - One life, no saves
- **Hunger System** - Starvation kills
- **Brutal Combat** - Every fight matters
- **Limited Resources** - Manage items carefully

### Character Classes
- **Warrior** - High HP and defense, melee specialist
- **Mage** - Master of magic with powerful spells
- **Rogue** - Critical strikes and stealth
- **Ranger** - Ranged combat and keen senses

### Procedural Generation
- Randomly generated dungeons every run
- Room-based level generation
- 26+ dungeon levels to conquer

### Rich Item System
- Weapons (melee & ranged)
- Armor (5 tiers)
- Potions (healing, strength, experience, etc.)
- Scrolls (identify, upgrade, teleport, etc.)
- Rings (planned)

### Enemy Types
- 11+ different mob types
- Boss encounters
- Intelligent AI (hunting, attacking, wandering)

## 🏗️ Build Instructions

This project uses Gradle and LibGDX for cross-platform support.

### Requirements
- JDK 11 or higher
- Gradle 8.5+

### Desktop Build
```bash
cd new-game
./gradlew desktop:run
```

### Android Build
```bash
cd new-game
./gradlew android:assembleDebug
```

### iOS Build (macOS only)
```bash
cd new-game
./gradlew ios:createIPA
```

## 🎯 Controls

- **WASD / Arrow Keys** - Move
- **ESC** - Back/Menu
- **ENTER** - Confirm

## 📦 Project Structure

```
new-game/
├── core/           # Shared game logic (Kotlin)
│   ├── actors/    # Hero, mobs, status effects
│   ├── items/     # Weapons, armor, consumables
│   ├── levels/    # Dungeon generation
│   ├── engine/    # Core game systems
│   └── scenes/    # Game screens
├── android/       # Android launcher
├── desktop/       # Desktop launcher
└── ios/          # iOS launcher
```

## 🚀 Roadmap

- [x] Core game engine
- [x] Procedural dungeon generation
- [x] 4 Character classes
- [x] Hunger system
- [x] Combat system
- [x] Item system (weapons, armor, potions, scrolls)
- [x] Enemy AI
- [ ] Status effects (buffs/debuffs)
- [ ] Field of view / Fog of war
- [ ] Inventory UI
- [ ] Save/load system
- [ ] Boss fights
- [ ] Sound effects
- [ ] Music
- [ ] Pixel art assets

## 📝 License

This is a ground-up rewrite, completely original code.
Licensed under MIT - you can use this commercially!

## 🎨 Design Philosophy

**Inspired by classic roguelikes but 100% original:**
- Turn-based tactical combat
- Procedural generation
- Permadeath
- Item identification
- Resource management

Built without using any GPL code - completely sellable!
