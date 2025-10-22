package com.meyrforge.heroscodex.feature_character_generator.domain.model

// Represents racial ability score bonuses in D&D 5e
data class RacialBonuses(
  val strength: Int = 0,
  val dexterity: Int = 0,
  val constitution: Int = 0,
  val intelligence: Int = 0,
  val wisdom: Int = 0,
  val charisma: Int = 0
) {
  companion object {
    // Hardcoded racial bonuses until we integrate with D&D API
    val RACIAL_BONUSES = mapOf(
      "dwarf" to RacialBonuses(constitution = 2),
      "elf" to RacialBonuses(dexterity = 2),
      "halfling" to RacialBonuses(dexterity = 2),
      "human" to RacialBonuses(
        strength = 1,
        dexterity = 1,
        constitution = 1,
        intelligence = 1,
        wisdom = 1,
        charisma = 1
      ),
      "dragonborn" to RacialBonuses(strength = 2, charisma = 1),
      "gnome" to RacialBonuses(intelligence = 2),
      "half-elf" to RacialBonuses(charisma = 2),
      "half-orc" to RacialBonuses(strength = 2, constitution = 1),
      "tiefling" to RacialBonuses(charisma = 2, intelligence = 1)
    )

    fun forRace(raceId: String): RacialBonuses {
      return RACIAL_BONUSES[raceId.lowercase()] ?: RacialBonuses()
    }
  }
}

