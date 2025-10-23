package com.meyrforge.heroscodex.feature_character_generator.domain.usecase

import com.meyrforge.heroscodex.feature_character_generator.domain.model.AbilityScoreGenerationMethod
import com.meyrforge.heroscodex.feature_character_generator.domain.model.AbilityScores
import com.meyrforge.heroscodex.feature_character_generator.domain.model.CharacterClass
import com.meyrforge.heroscodex.feature_character_generator.domain.model.RacialBonuses
import com.meyrforge.heroscodex.feature_character_generator.domain.repository.CharacterRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class GenerateAbilityScoresUseCaseTest {

  private lateinit var characterRepository: CharacterRepository
  private lateinit var useCase: GenerateAbilityScoresUseCase

  @Before
  fun setUp() {
    characterRepository = mockk()
    useCase = GenerateAbilityScoresUseCase(characterRepository)
  }

  @Test
  fun `should generate all ability scores within valid range`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first { it.id == "wizard" }
    val racialBonuses = RacialBonuses.forRace("elf")

    every { characterRepository.getRacialBonuses("elf") } returns racialBonuses

    val abilityScores = useCase.generate(
      characterClass = characterClass,
      raceId = "elf",
      method = AbilityScoreGenerationMethod.FOUR_D6_DROP_LOWEST
    )

    val allScoresValid = listOf(
      abilityScores.strength,
      abilityScores.dexterity,
      abilityScores.constitution,
      abilityScores.intelligence,
      abilityScores.wisdom,
      abilityScores.charisma
    ).all { it in AbilityScores.MIN_SCORE..20 }

    assertTrue("All ability scores should be within valid range (3-20)", allScoresValid)
  }

  @Test
  fun `should apply racial bonuses to total score`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first { it.id == "fighter" }
    val racialBonuses = RacialBonuses(strength = 2, constitution = 1)

    every { characterRepository.getRacialBonuses("half-orc") } returns racialBonuses

    val abilityScores = useCase.generate(
      characterClass = characterClass,
      raceId = "half-orc",
      method = AbilityScoreGenerationMethod.STANDARD_ARRAY
    )

    val total = abilityScores.total()
    assertTrue("Total should include racial bonuses (+3)", total >= 75)
  }

  @Test
  fun `should prioritize class primary abilities`() {
    val wizard = CharacterClass.AVAILABLE_CLASSES.first { it.id == "wizard" }
    val racialBonuses = RacialBonuses.forRace("human")

    every { characterRepository.getRacialBonuses("human") } returns racialBonuses

    val abilityScores = useCase.generate(
      characterClass = wizard,
      raceId = "human",
      method = AbilityScoreGenerationMethod.STANDARD_ARRAY
    )

    assertTrue("Intelligence should be high for wizard", abilityScores.intelligence >= 13)
  }

  @Test
  fun `should generate using standard array method`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first { it.id == "fighter" }
    val racialBonuses = RacialBonuses.forRace("human")

    every { characterRepository.getRacialBonuses("human") } returns racialBonuses

    val abilityScores = useCase.generate(
      characterClass = characterClass,
      raceId = "human",
      method = AbilityScoreGenerationMethod.STANDARD_ARRAY
    )

    assertEquals("Total with human bonuses should be 78", 78, abilityScores.total())
  }

  @Test
  fun `should generate different scores on multiple dice rolls`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first { it.id == "rogue" }
    val racialBonuses = RacialBonuses.forRace("halfling")

    every { characterRepository.getRacialBonuses("halfling") } returns racialBonuses

    val generatedScores = mutableSetOf<Int>()

    repeat(10) {
      val abilityScores = useCase.generate(
        characterClass = characterClass,
        raceId = "halfling",
        method = AbilityScoreGenerationMethod.FOUR_D6_DROP_LOWEST
      )
      generatedScores.add(abilityScores.total())
    }

    assertTrue("Should generate variety in totals with dice rolls", generatedScores.size >= 2)
  }

  @Test
  fun `should calculate all ability modifiers correctly`() {
    val abilityScores = AbilityScores(
      strength = 16,
      dexterity = 14,
      constitution = 12,
      intelligence = 10,
      wisdom = 8,
      charisma = 6
    )

    assertEquals("STR 16 should have +3 modifier", 3, abilityScores.getStrengthModifier())
    assertEquals("DEX 14 should have +2 modifier", 2, abilityScores.getDexterityModifier())
    assertEquals("CON 12 should have +1 modifier", 1, abilityScores.getConstitutionModifier())
    assertEquals("INT 10 should have 0 modifier", 0, abilityScores.getIntelligenceModifier())
    assertEquals("WIS 8 should have -1 modifier", -1, abilityScores.getWisdomModifier())
    assertEquals("CHA 6 should have -2 modifier", -2, abilityScores.getCharismaModifier())
  }

  @Test
  fun `should respect minimum ability score limits`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first()
    val racialBonuses = RacialBonuses.forRace("human")

    every { characterRepository.getRacialBonuses("human") } returns racialBonuses

    repeat(20) {
      val abilityScores = useCase.generate(
        characterClass = characterClass,
        raceId = "human",
        method = AbilityScoreGenerationMethod.FOUR_D6_DROP_LOWEST
      )

      val allScoresAboveMin = listOf(
        abilityScores.strength,
        abilityScores.dexterity,
        abilityScores.constitution,
        abilityScores.intelligence,
        abilityScores.wisdom,
        abilityScores.charisma
      ).all { it >= AbilityScores.MIN_SCORE }

      assertTrue("All scores should be >= 3", allScoresAboveMin)
    }
  }

  @Test
  fun `should respect maximum ability score without racial bonuses`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first { it.id == "barbarian" }
    val racialBonuses = RacialBonuses()

    every { characterRepository.getRacialBonuses("none") } returns racialBonuses

    repeat(20) {
      val abilityScores = useCase.generate(
        characterClass = characterClass,
        raceId = "none",
        method = AbilityScoreGenerationMethod.FOUR_D6_DROP_LOWEST
      )

      val allScoresBelowMax = listOf(
        abilityScores.strength,
        abilityScores.dexterity,
        abilityScores.constitution,
        abilityScores.intelligence,
        abilityScores.wisdom,
        abilityScores.charisma
      ).all { it <= AbilityScores.MAX_SCORE }

      assertTrue("All scores without bonuses should be <= 18", allScoresBelowMax)
    }
  }

  @Test
  fun `should allow up to 20 with racial bonuses`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first { it.id == "fighter" }
    val racialBonuses = RacialBonuses(strength = 2)

    every { characterRepository.getRacialBonuses("dragonborn") } returns racialBonuses

    val abilityScores = useCase.generate(
      characterClass = characterClass,
      raceId = "dragonborn",
      method = AbilityScoreGenerationMethod.STANDARD_ARRAY
    )

    assertTrue("Strength with racial bonus can reach up to 20", abilityScores.strength <= 20)
  }

  @Test
  fun `should generate using manual array method`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first { it.id == "wizard" }
    val racialBonuses = RacialBonuses.forRace("elf")

    every { characterRepository.getRacialBonuses("elf") } returns racialBonuses

    val abilityScores = useCase.generate(
      characterClass = characterClass,
      raceId = "elf",
      method = AbilityScoreGenerationMethod.MANUAL_ARRAY
    )

    val total = abilityScores.total()
    assertTrue("Total should be standard array (72) + elf bonuses", total >= 72)
    assertTrue("Intelligence should be high for wizard", abilityScores.intelligence >= 13)
  }

  @Test
  fun `should generate using manual point buy method`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first { it.id == "rogue" }
    val racialBonuses = RacialBonuses.forRace("halfling")

    every { characterRepository.getRacialBonuses("halfling") } returns racialBonuses

    val abilityScores = useCase.generate(
      characterClass = characterClass,
      raceId = "halfling",
      method = AbilityScoreGenerationMethod.MANUAL_POINT_BUY
    )

    assertTrue("Dexterity should be high for rogue", abilityScores.dexterity >= 13)
  }

  @Test
  fun `should call repository for racial bonuses`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first { it.id == "wizard" }
    val racialBonuses = RacialBonuses.forRace("elf")

    every { characterRepository.getRacialBonuses("elf") } returns racialBonuses

    useCase.generate(
      characterClass = characterClass,
      raceId = "elf",
      method = AbilityScoreGenerationMethod.FOUR_D6_DROP_LOWEST
    )

    verify { characterRepository.getRacialBonuses("elf") }
  }
}



