package com.meyrforge.heroscodex.feature_character_generator.domain.model

data class CharacterClass(
  val id: String,
  val name: String,
  val description: String,
  val hitDice: String,
  val primaryAbility: List<String>
) {
  companion object {
    val AVAILABLE_CLASSES = listOf(
      CharacterClass(
        id = "barbarian",
        name = "Barbarian",
        description = "A fierce warrior of primitive background who can enter a battle rage",
        hitDice = "1d12",
        primaryAbility = listOf("Strength")
      ),
      CharacterClass(
        id = "bard",
        name = "Bard",
        description = "An inspiring magician whose power echoes the music of creation",
        hitDice = "1d8",
        primaryAbility = listOf("Charisma")
      ),
      CharacterClass(
        id = "cleric",
        name = "Cleric",
        description = "A priestly champion who wields divine magic in service of a higher power",
        hitDice = "1d8",
        primaryAbility = listOf("Wisdom")
      ),
      CharacterClass(
        id = "druid",
        name = "Druid",
        description = "A priest of the Old Faith, wielding the powers of nature and adopting animal forms",
        hitDice = "1d8",
        primaryAbility = listOf("Wisdom")
      ),
      CharacterClass(
        id = "fighter",
        name = "Fighter",
        description = "A master of martial combat, skilled with a variety of weapons and armor",
        hitDice = "1d10",
        primaryAbility = listOf("Strength", "Dexterity")
      ),
      CharacterClass(
        id = "monk",
        name = "Monk",
        description = "A master of martial arts, harnessing the power of the body in pursuit of physical and spiritual perfection",
        hitDice = "1d8",
        primaryAbility = listOf("Dexterity", "Wisdom")
      ),
      CharacterClass(
        id = "paladin",
        name = "Paladin",
        description = "A holy warrior bound to a sacred oath",
        hitDice = "1d10",
        primaryAbility = listOf("Strength", "Charisma")
      ),
      CharacterClass(
        id = "ranger",
        name = "Ranger",
        description = "A warrior who uses martial prowess and nature magic to combat threats on the edges of civilization",
        hitDice = "1d10",
        primaryAbility = listOf("Dexterity", "Wisdom")
      ),
      CharacterClass(
        id = "rogue",
        name = "Rogue",
        description = "A scoundrel who uses stealth and trickery to overcome obstacles and enemies",
        hitDice = "1d8",
        primaryAbility = listOf("Dexterity")
      ),
      CharacterClass(
        id = "sorcerer",
        name = "Sorcerer",
        description = "A spellcaster who draws on inherent magic from a gift or bloodline",
        hitDice = "1d6",
        primaryAbility = listOf("Charisma")
      ),
      CharacterClass(
        id = "warlock",
        name = "Warlock",
        description = "A wielder of magic that is derived from a bargain with an extraplanar entity",
        hitDice = "1d8",
        primaryAbility = listOf("Charisma")
      ),
      CharacterClass(
        id = "wizard",
        name = "Wizard",
        description = "A scholarly magic-user capable of manipulating the structures of reality",
        hitDice = "1d6",
        primaryAbility = listOf("Intelligence")
      )
    )
  }
}

