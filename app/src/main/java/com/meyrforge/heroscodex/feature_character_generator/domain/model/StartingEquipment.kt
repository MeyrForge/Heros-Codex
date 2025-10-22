package com.meyrforge.heroscodex.feature_character_generator.domain.model

data class StartingEquipment(
  val weapons: List<Item>,
  val armor: List<Item>,
  val gear: List<Item>,
  val startingGold: Int
) {
  companion object {
    private val EXPLORERS_PACK = Item(
      "explorers_pack",
      "Explorer's Pack",
      "Includes a backpack, bedroll, mess kit, tinderbox, torches, rations, and waterskin",
      "Adventuring Gear"
    )

    private val DUNGONEERS_PACK = Item(
      "dungoneers_pack",
      "Dungeoneer's Pack",
      "Includes a backpack, crowbar, hammer, pitons, torches, tinderbox, rations, waterskin, and rope",
      "Adventuring Gear"
    )

    private val PRIESTS_PACK = Item(
      "priests_pack",
      "Priest's Pack",
      "Includes a backpack, blanket, candles, tinderbox, alms box, incense, censer, vestments, rations, and waterskin",
      "Adventuring Gear"
    )

    private val ENTERTAINERS_PACK = Item(
      "entertainers_pack",
      "Entertainer's Pack",
      "Includes a backpack, bedroll, costumes, candles, rations, and waterskin",
      "Adventuring Gear"
    )

    private val SCHOLARS_PACK = Item(
      "scholars_pack",
      "Scholar's Pack",
      "Includes a backpack, book of lore, ink, ink pen, parchment, sand, and small knife",
      "Adventuring Gear"
    )

    private val BURGLARS_PACK = Item(
      "burglars_pack",
      "Burglar's Pack",
      "Includes a backpack, ball bearings, string, bell, candles, crowbar, hammer, pitons, hooded lantern, oil, rations, tinderbox, waterskin, and rope",
      "Adventuring Gear"
    )

    private val SHIELD = Item(
      "shield",
      "Shield",
      "Wooden or metal protection carried in one hand",
      "Shield"
    )

    private val HOLY_SYMBOL = Item(
      "holy_symbol",
      "Holy Symbol",
      "A representation of a deity or pantheon",
      "Holy Symbol"
    )

    private val ARROWS = Item(
      "arrows",
      "Arrows",
      "Ammunition for bows",
      "Ammunition",
      quantity = 20
    )

    private val CROSSBOW_BOLTS = Item(
      "crossbow_bolts",
      "Crossbow Bolts",
      "Ammunition for crossbows",
      "Ammunition",
      quantity = 20
    )

    private val JAVELIN = Item(
      "javelin",
      "Javelin",
      "A thrown spear",
      "Weapon",
      properties = listOf("Thrown")
    )

    private val DART = Item(
      "dart",
      "Dart",
      "A small thrown weapon",
      "Weapon",
      properties = listOf("Finesse", "Thrown")
    )

    private val DAGGER = Item(
      "dagger",
      "Dagger",
      "A short blade for close combat",
      "Weapon",
      properties = listOf("Finesse", "Light", "Thrown")
    )

    private val LEATHER_ARMOR = Item(
      "leather_armor",
      "Leather Armor",
      "Light, flexible protection",
      "Light Armor"
    )

    fun getEquipmentForClass(characterClass: CharacterClass): StartingEquipment {
      return when (characterClass.id) {
        "barbarian" -> barbarianEquipment()
        "bard" -> bardEquipment()
        "cleric" -> clericEquipment()
        "druid" -> druidEquipment()
        "fighter" -> fighterEquipment()
        "monk" -> monkEquipment()
        "paladin" -> paladinEquipment()
        "ranger" -> rangerEquipment()
        "rogue" -> rogueEquipment()
        "sorcerer" -> sorcererEquipment()
        "warlock" -> warlockEquipment()
        "wizard" -> wizardEquipment()
        else -> StartingEquipment(emptyList(), emptyList(), emptyList(), 0)
      }
    }

    private fun barbarianEquipment() = StartingEquipment(
      weapons = listOf(
        Item(
          "greataxe",
          "Greataxe",
          "A heavy two-handed axe",
          "Weapon",
          properties = listOf("Heavy", "Two-Handed")
        ),
        Item(
          "handaxe",
          "Handaxe",
          "A light throwing axe",
          "Weapon",
          quantity = 2,
          properties = listOf("Light", "Thrown")
        ),
        Item(
          "javelin",
          "Javelin",
          "A thrown spear",
          "Weapon",
          quantity = 4,
          properties = listOf("Thrown")
        )
      ),
      armor = emptyList(),
      gear = listOf(EXPLORERS_PACK),
      startingGold = 20
    )

    private fun bardEquipment() = StartingEquipment(
      weapons = listOf(
        Item(
          "rapier",
          "Rapier",
          "A slender, sharply pointed sword",
          "Weapon",
          properties = listOf("Finesse")
        ),
        DAGGER
      ),
      armor = listOf(
        LEATHER_ARMOR
      ),
      gear = listOf(
        Item("lute", "Lute", "A stringed musical instrument", "Musical Instrument"),
        ENTERTAINERS_PACK
      ),
      startingGold = 50
    )

    private fun clericEquipment() = StartingEquipment(
      weapons = listOf(
        Item("mace", "Mace", "A heavy club with a metal head", "Weapon")
      ),
      armor = listOf(
        Item("scale_mail", "Scale Mail", "Armor made of overlapping metal scales", "Medium Armor"),
        SHIELD
      ),
      gear = listOf(
        HOLY_SYMBOL,
        PRIESTS_PACK
      ),
      startingGold = 50
    )

    private fun druidEquipment() = StartingEquipment(
      weapons = listOf(
        Item(
          "scimitar",
          "Scimitar",
          "A curved sword",
          "Weapon",
          properties = listOf("Finesse", "Light")
        )
      ),
      armor = listOf(
        LEATHER_ARMOR,
        SHIELD
      ),
      gear = listOf(
        Item(
          "druidic_focus",
          "Druidic Focus",
          "A wooden staff or totem used to channel druidic magic",
          "Druidic Focus"
        ),
        EXPLORERS_PACK
      ),
      startingGold = 20
    )

    private fun fighterEquipment() = StartingEquipment(
      weapons = listOf(
        Item(
          "longsword",
          "Longsword",
          "A versatile blade",
          "Weapon",
          properties = listOf("Versatile")
        ),
        SHIELD,
        Item(
          "crossbow_light",
          "Light Crossbow",
          "A ranged weapon",
          "Weapon",
          properties = listOf("Ammunition", "Loading", "Two-Handed")
        ),
        CROSSBOW_BOLTS
      ),
      armor = listOf(
        Item("chain_mail", "Chain Mail", "Armor made of interlocking metal rings", "Heavy Armor")
      ),
      gear = listOf(DUNGONEERS_PACK),
      startingGold = 100
    )

    private fun monkEquipment() = StartingEquipment(
      weapons = listOf(
        Item(
          "shortsword",
          "Shortsword",
          "A short blade",
          "Weapon",
          properties = listOf("Finesse", "Light")
        ),
        DART.copy(quantity = 10)
      ),
      armor = emptyList(),
      gear = listOf(EXPLORERS_PACK),
      startingGold = 50
    )

    private fun paladinEquipment() = StartingEquipment(
      weapons = listOf(
        Item(
          "longsword_paladin",
          "Longsword",
          "A versatile blade",
          "Weapon",
          properties = listOf("Versatile")
        ),
        SHIELD,
        JAVELIN.copy(quantity = 5)
      ),
      armor = listOf(
        Item(
          "chain_mail_paladin",
          "Chain Mail",
          "Armor made of interlocking metal rings",
          "Heavy Armor"
        )
      ),
      gear = listOf(
        HOLY_SYMBOL,
        PRIESTS_PACK
      ),
      startingGold = 100
    )

    private fun rangerEquipment() = StartingEquipment(
      weapons = listOf(
        Item(
          "longbow",
          "Longbow",
          "A powerful ranged weapon",
          "Weapon",
          properties = listOf("Ammunition", "Heavy", "Two-Handed")
        ),
        ARROWS,
        Item(
          "shortsword_ranger",
          "Shortsword",
          "A short blade",
          "Weapon",
          quantity = 2,
          properties = listOf("Finesse", "Light")
        )
      ),
      armor = listOf(
        LEATHER_ARMOR
      ),
      gear = listOf(EXPLORERS_PACK),
      startingGold = 100
    )

    private fun rogueEquipment() = StartingEquipment(
      weapons = listOf(
        Item(
          "rapier_rogue",
          "Rapier",
          "A slender, sharply pointed sword",
          "Weapon",
          properties = listOf("Finesse")
        ),
        Item(
          "shortbow",
          "Shortbow",
          "A compact ranged weapon",
          "Weapon",
          properties = listOf("Ammunition", "Two-Handed")
        ),
        ARROWS,
        DAGGER
      ),
      armor = listOf(
        LEATHER_ARMOR
      ),
      gear = listOf(
        Item(
          "thieves_tools",
          "Thieves' Tools",
          "Specialized tools for picking locks and disarming traps",
          "Tool"
        ),
        BURGLARS_PACK
      ),
      startingGold = 40
    )

    private fun sorcererEquipment() = StartingEquipment(
      weapons = listOf(
        Item(
          "light_crossbow_sorcerer",
          "Light Crossbow",
          "A ranged weapon",
          "Weapon",
          properties = listOf("Ammunition", "Loading", "Two-Handed")
        ),
        CROSSBOW_BOLTS,
        DAGGER.copy(quantity = 2)
      ),
      armor = emptyList(),
      gear = listOf(
        Item(
          "arcane_focus_sorcerer",
          "Arcane Focus",
          "A crystal, orb, rod, staff, or wand used to channel arcane magic",
          "Arcane Focus"
        ),
        DUNGONEERS_PACK
      ),
      startingGold = 30
    )

    private fun warlockEquipment() = StartingEquipment(
      weapons = listOf(
        Item(
          "light_crossbow_warlock",
          "Light Crossbow",
          "A ranged weapon",
          "Weapon",
          properties = listOf("Ammunition", "Loading", "Two-Handed")
        ),
        CROSSBOW_BOLTS,
        DAGGER.copy(quantity = 2)
      ),
      armor = listOf(
        LEATHER_ARMOR
      ),
      gear = listOf(
        Item(
          "arcane_focus_warlock",
          "Arcane Focus",
          "A crystal, orb, rod, staff, or wand used to channel arcane magic",
          "Arcane Focus"
        ),
        SCHOLARS_PACK
      ),
      startingGold = 40
    )

    private fun wizardEquipment() = StartingEquipment(
      weapons = listOf(
        Item(
          "quarterstaff",
          "Quarterstaff",
          "A simple wooden staff",
          "Weapon",
          properties = listOf("Versatile")
        ),
        DAGGER
      ),
      armor = emptyList(),
      gear = listOf(
        Item(
          "arcane_focus_wizard",
          "Arcane Focus",
          "A crystal, orb, rod, staff, or wand used to channel arcane magic",
          "Arcane Focus"
        ),
        Item("spellbook", "Spellbook", "A leather-bound tome containing spells", "Spellbook"),
        SCHOLARS_PACK
      ),
      startingGold = 30
    )
  }
}
