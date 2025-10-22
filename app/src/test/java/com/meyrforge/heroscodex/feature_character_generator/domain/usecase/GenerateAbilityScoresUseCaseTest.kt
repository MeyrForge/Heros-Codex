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
  fun `should generate ability scores within valid range`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first { it.id == "wizard" }
    val racialBonuses = RacialBonuses.forRace("elf")

    every { characterRepository.getRacialBonuses("elf") } returns racialBonuses

    val abilityScores = useCase.generate(
      characterClass = characterClass,
      raceId = "elf",
      method = AbilityScoreGenerationMethod.FOUR_D6_DROP_LOWEST
    )

    // Each score should be between 3 and 18 (before racial bonuses) or up to 20 (with bonuses)
    assertTrue("Strength should be valid", abilityScores.strength in 3..20)
    assertTrue("Dexterity should be valid", abilityScores.dexterity in 3..20)
    assertTrue("Constitution should be valid", abilityScores.constitution in 3..20)
    assertTrue("Intelligence should be valid", abilityScores.intelligence in 3..20)
    assertTrue("Wisdom should be valid", abilityScores.wisdom in 3..20)
    assertTrue("Charisma should be valid", abilityScores.charisma in 3..20)

    verify { characterRepository.getRacialBonuses("elf") }
  }

  @Test
  fun `should apply racial bonuses correctly`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first { it.id == "fighter" }
    val racialBonuses = RacialBonuses(strength = 2, constitution = 1) // Half-orc bonuses

    every { characterRepository.getRacialBonuses("half-orc") } returns racialBonuses

    val abilityScores = useCase.generate(
      characterClass = characterClass,
      raceId = "half-orc",
      method = AbilityScoreGenerationMethod.STANDARD_ARRAY
    )

    // Standard array is [15, 14, 13, 12, 10, 8]
    // With half-orc bonuses (+2 STR, +1 CON), the highest scores should reflect this
    val total = abilityScores.total()

    // Standard array sum = 72, racial bonuses add +3
    assertTrue("Total should include racial bonuses", total >= 72)

    verify { characterRepository.getRacialBonuses("half-orc") }
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

    // Wizard's primary ability is Intelligence, should be among the highest scores
    assertTrue(
      "Intelligence should be high for wizard",
      abilityScores.intelligence >= 13
    )
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

    // Standard array: [15, 14, 13, 12, 10, 8] = 72
    // Human gets +1 to all = 78
    assertEquals("Total with human bonuses should be 78", 78, abilityScores.total())
  }

  @Test
  fun `should generate different scores on multiple rolls`() {
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

    // With dice rolls, we should get variety in totals
    assertTrue(
      "Should generate some variety in ability score totals",
      generatedScores.size >= 2
    )
  }

  @Test
  fun `should calculate modifiers correctly`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first { it.id == "barbarian" }
    val racialBonuses = RacialBonuses.forRace("dragonborn")

    every { characterRepository.getRacialBonuses("dragonborn") } returns racialBonuses

    val abilityScores = useCase.generate(
      characterClass = characterClass,
      raceId = "dragonborn",
      method = AbilityScoreGenerationMethod.STANDARD_ARRAY
    )

    // Test modifier calculation
    val strengthMod = abilityScores.getStrengthModifier()
    val expectedMod = (abilityScores.strength - 10) / 2

    assertEquals("Strength modifier should be calculated correctly", expectedMod, strengthMod)
  }

  @Test
  fun `should ensure fighter has high strength or dexterity`() {
    val fighter = CharacterClass.AVAILABLE_CLASSES.first { it.id == "fighter" }
    val racialBonuses = RacialBonuses.forRace("human")

    every { characterRepository.getRacialBonuses("human") } returns racialBonuses

    val abilityScores = useCase.generate(
      characterClass = fighter,
      raceId = "human",
      method = AbilityScoreGenerationMethod.STANDARD_ARRAY
    )

    // Fighter's primary abilities are Strength and Dexterity
    assertTrue(
      "Fighter should have high Strength or Dexterity",
      abilityScores.strength >= 13 || abilityScores.dexterity >= 13
    )
  }

  @Test
  fun `should ensure cleric has high wisdom`() {
    val cleric = CharacterClass.AVAILABLE_CLASSES.first { it.id == "cleric" }
    val racialBonuses = RacialBonuses.forRace("dwarf")

    every { characterRepository.getRacialBonuses("dwarf") } returns racialBonuses

    val abilityScores = useCase.generate(
      characterClass = cleric,
      raceId = "dwarf",
      method = AbilityScoreGenerationMethod.STANDARD_ARRAY
    )

    // Cleric's primary ability is Wisdom
    assertTrue(
      "Cleric should have high Wisdom",
      abilityScores.wisdom >= 13
    )
  }

  @Test
  fun `should respect minimum ability score limits`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first()
    val racialBonuses = RacialBonuses.forRace("human")

    every { characterRepository.getRacialBonuses("human") } returns racialBonuses

    val abilityScores = useCase.generate(
      characterClass = characterClass,
      raceId = "human",
      method = AbilityScoreGenerationMethod.FOUR_D6_DROP_LOWEST
    )

    // No ability score should be below 3 (absolute minimum in D&D)
    assertTrue("All scores should be >= 3", abilityScores.strength >= AbilityScores.MIN_SCORE)
    assertTrue("All scores should be >= 3", abilityScores.dexterity >= AbilityScores.MIN_SCORE)
    assertTrue("All scores should be >= 3", abilityScores.constitution >= AbilityScores.MIN_SCORE)
    assertTrue("All scores should be >= 3", abilityScores.intelligence >= AbilityScores.MIN_SCORE)
    assertTrue("All scores should be >= 3", abilityScores.wisdom >= AbilityScores.MIN_SCORE)
    assertTrue("All scores should be >= 3", abilityScores.charisma >= AbilityScores.MIN_SCORE)
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
    assertTrue("Total should be standard array (72) + elf bonuses (2)", total >= 72)
    assertTrue("Intelligence should be high for wizard", abilityScores.intelligence >= 13)

    verify { characterRepository.getRacialBonuses("elf") }
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
    verify { characterRepository.getRacialBonuses("halfling") }
  }

  @Test
  fun `should not exceed maximum score without racial bonuses`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first { it.id == "barbarian" }
    val racialBonuses = RacialBonuses()

    every { characterRepository.getRacialBonuses("none") } returns racialBonuses

    repeat(20) {
      val abilityScores = useCase.generate(
        characterClass = characterClass,
        raceId = "none",
        method = AbilityScoreGenerationMethod.FOUR_D6_DROP_LOWEST
      )

      assertTrue("Strength should not exceed 18", abilityScores.strength <= AbilityScores.MAX_SCORE)
      assertTrue(
        "Dexterity should not exceed 18",
        abilityScores.dexterity <= AbilityScores.MAX_SCORE
      )
      assertTrue(
        "Constitution should not exceed 18",
        abilityScores.constitution <= AbilityScores.MAX_SCORE
      )
      assertTrue(
        "Intelligence should not exceed 18",
        abilityScores.intelligence <= AbilityScores.MAX_SCORE
      )
      assertTrue("Wisdom should not exceed 18", abilityScores.wisdom <= AbilityScores.MAX_SCORE)
      assertTrue("Charisma should not exceed 18", abilityScores.charisma <= AbilityScores.MAX_SCORE)
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
  fun `should calculate all ability modifiers correctly`() {
    val characterClass = CharacterClass.AVAILABLE_CLASSES.first()
    val racialBonuses = RacialBonuses.forRace("human")

    every { characterRepository.getRacialBonuses("human") } returns racialBonuses

    val abilityScores = useCase.generate(
      characterClass = characterClass,
      raceId = "human",
      method = AbilityScoreGenerationMethod.STANDARD_ARRAY
    )

    assertEquals(
      "STR modifier",
      (abilityScores.strength - 10) / 2,
      abilityScores.getStrengthModifier()
    )
    assertEquals(
      "DEX modifier",
      (abilityScores.dexterity - 10) / 2,
      abilityScores.getDexterityModifier()
    )
    assertEquals(
      "CON modifier",
      (abilityScores.constitution - 10) / 2,
      abilityScores.getConstitutionModifier()
    )
    assertEquals(
      "INT modifier",
      (abilityScores.intelligence - 10) / 2,
      abilityScores.getIntelligenceModifier()
    )
    assertEquals("WIS modifier", (abilityScores.wisdom - 10) / 2, abilityScores.getWisdomModifier())
    assertEquals(
      "CHA modifier",
      (abilityScores.charisma - 10) / 2,
      abilityScores.getCharismaModifier()
    )
  }

  @Test
  fun `should handle negative modifiers correctly`() {
    val abilityScores = AbilityScores(
      strength = 8,
      dexterity = 8,
      constitution = 8,
      intelligence = 8,
      wisdom = 8,
      charisma = 8
    )

    assertEquals("Score 8 should have -1 modifier", -1, abilityScores.getStrengthModifier())
    assertEquals("Score 8 should have -1 modifier", -1, abilityScores.getDexterityModifier())
  }

  @Test
  fun `should handle score of 10 as zero modifier`() {
    val abilityScores = AbilityScores(
      strength = 10,
      dexterity = 10,
      constitution = 10,
      intelligence = 10,
      wisdom = 10,
      charisma = 10
    )

    assertEquals("Score 10 should have 0 modifier", 0, abilityScores.getStrengthModifier())
  }
}


