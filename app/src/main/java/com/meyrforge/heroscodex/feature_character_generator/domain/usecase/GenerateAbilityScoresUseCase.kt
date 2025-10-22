package com.meyrforge.heroscodex.feature_character_generator.domain.usecase

import com.meyrforge.heroscodex.feature_character_generator.domain.model.AbilityScoreGenerationMethod
import com.meyrforge.heroscodex.feature_character_generator.domain.model.AbilityScores
import com.meyrforge.heroscodex.feature_character_generator.domain.model.CharacterClass
import com.meyrforge.heroscodex.feature_character_generator.domain.model.RacialBonuses
import com.meyrforge.heroscodex.feature_character_generator.domain.repository.CharacterRepository
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class GenerateAbilityScoresUseCase @Inject constructor(
  private val characterRepository: CharacterRepository
) {

  fun generate(
    characterClass: CharacterClass,
    raceId: String,
    method: AbilityScoreGenerationMethod = AbilityScoreGenerationMethod.FOUR_D6_DROP_LOWEST
  ): AbilityScores {
    val racialBonuses = characterRepository.getRacialBonuses(raceId)

    val baseScores = when (method) {
      AbilityScoreGenerationMethod.FOUR_D6_DROP_LOWEST -> generateByDiceRoll()
      AbilityScoreGenerationMethod.STANDARD_ARRAY -> AbilityScores.STANDARD_ARRAY
      AbilityScoreGenerationMethod.MANUAL_ARRAY -> AbilityScores.STANDARD_ARRAY
      AbilityScoreGenerationMethod.MANUAL_POINT_BUY -> generateDefaultPointBuy()
    }

    return assignScoresToAbilities(baseScores, characterClass, racialBonuses)
  }

  private fun generateByDiceRoll(): List<Int> {
    return (1..6).map {
      val rolls = (1..4).map { (1..6).random() }.sorted()
      val sum = rolls.drop(1).sum()
      min(sum, AbilityScores.MAX_SCORE)
    }.sortedDescending()
  }

  private fun generateDefaultPointBuy(): List<Int> {
    // Default balanced distribution
    // Base 8 + using 27 points optimally results in standard array equivalent
    return listOf(15, 14, 13, 12, 10, 8)
  }


  private fun assignScoresToAbilities(
    baseScores: List<Int>,
    characterClass: CharacterClass,
    racialBonuses: RacialBonuses
  ): AbilityScores {
    val sortedScores = baseScores.sortedDescending().toMutableList()
    val abilityMap = mutableMapOf<String, Int>()
    val primaryAbilities = characterClass.primaryAbility.map { it.lowercase() }

    primaryAbilities.forEachIndexed { index, ability ->
      if (index < sortedScores.size) {
        abilityMap[ability] = sortedScores.removeAt(0)
      }
    }

    val remainingAbilities =
      listOf("strength", "dexterity", "constitution", "intelligence", "wisdom", "charisma")
        .filter { it !in abilityMap.keys }

    remainingAbilities.forEachIndexed { index, ability ->
      if (index < sortedScores.size) {
        abilityMap[ability] = sortedScores[index]
      } else {
        abilityMap[ability] = 10
      }
    }

    return AbilityScores(
      strength = capScore((abilityMap["strength"] ?: 10) + racialBonuses.strength),
      dexterity = capScore((abilityMap["dexterity"] ?: 10) + racialBonuses.dexterity),
      constitution = capScore((abilityMap["constitution"] ?: 10) + racialBonuses.constitution),
      intelligence = capScore((abilityMap["intelligence"] ?: 10) + racialBonuses.intelligence),
      wisdom = capScore((abilityMap["wisdom"] ?: 10) + racialBonuses.wisdom),
      charisma = capScore((abilityMap["charisma"] ?: 10) + racialBonuses.charisma)
    )
  }

  private fun capScore(score: Int): Int {
    return max(AbilityScores.MIN_SCORE, min(score, 20))
  }
}

