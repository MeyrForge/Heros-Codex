package com.meyrforge.heroscodex.feature_character_generator.domain.model

// Represents the six ability scores in D&D 5e
// Each score typically ranges from 3 (lowest) to 18 (highest) at level 1
data class AbilityScores(
  val strength: Int,
  val dexterity: Int,
  val constitution: Int,
  val intelligence: Int,
  val wisdom: Int,
  val charisma: Int
) {
  // Calculate the modifier for a given ability score
  // Formula: (score - 10) / 2, rounded down
  fun getModifier(score: Int): Int = (score - 10) / 2

  fun getStrengthModifier(): Int = getModifier(strength)
  fun getDexterityModifier(): Int = getModifier(dexterity)
  fun getConstitutionModifier(): Int = getModifier(constitution)
  fun getIntelligenceModifier(): Int = getModifier(intelligence)
  fun getWisdomModifier(): Int = getModifier(wisdom)
  fun getCharismaModifier(): Int = getModifier(charisma)

  fun total(): Int = strength + dexterity + constitution + intelligence + wisdom + charisma

  companion object {
    const val MIN_SCORE = 3 // Absolute minimum (only from bad dice rolls)
    const val MAX_SCORE = 18
    const val AVERAGE_SCORE = 10

    // Standard array method for ability scores in D&D 5e
    val STANDARD_ARRAY = listOf(15, 14, 13, 12, 10, 8)

    // Point Buy system (D&D 5e official rules)
    const val POINT_BUY_BASE = 8 // All abilities start at 8 in Point Buy
    const val POINT_BUY_MIN = 8 // Cannot go below 8 in Point Buy
    const val POINT_BUY_MAX = 15 // Cannot go above 15 in Point Buy (before racial bonuses)
    const val POINT_BUY_TOTAL = 27 // Total points to distribute (D&D 5e official)
  }
}

