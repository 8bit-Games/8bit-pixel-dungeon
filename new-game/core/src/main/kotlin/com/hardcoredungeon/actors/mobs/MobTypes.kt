package com.hardcoredungeon.actors.mobs

/**
 * Concrete mob types
 */
class Rat : Mob("Rat", baseHP = 8, attack = 2, defense = 0, expValue = 5)

class Gnoll : Mob("Gnoll", baseHP = 12, attack = 4, defense = 1, expValue = 10)

class Crab : Mob("Crab", baseHP = 15, attack = 3, defense = 3, expValue = 8)

class Skeleton : Mob("Skeleton", baseHP = 20, attack = 6, defense = 2, expValue = 15)

class Thief : Mob("Thief", baseHP = 16, attack = 5, defense = 1, expValue = 12)

class Bat : Mob("Bat", baseHP = 10, attack = 3, defense = 0, expValue = 7)

class Snake : Mob("Snake", baseHP = 14, attack = 4, defense = 1, expValue = 10)

class Golem : Mob("Golem", baseHP = 40, attack = 10, defense = 5, expValue = 30)

// Boss mobs
class GooBlob : Mob("Goo Blob", baseHP = 80, attack = 12, defense = 3, expValue = 100)

class TenguBoss : Mob("Tengu", baseHP = 120, attack = 15, defense = 5, expValue = 200)

class DemonLord : Mob("Demon Lord", baseHP = 200, attack = 20, defense = 8, expValue = 500)
