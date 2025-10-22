package com.meyrforge.heroscodex.feature_character_generator.domain.usecase

import com.meyrforge.heroscodex.feature_character_generator.domain.model.CharacterClass
import com.meyrforge.heroscodex.feature_character_generator.domain.model.Feature
import com.meyrforge.heroscodex.feature_character_generator.domain.repository.CharacterRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class GenerateFeaturesUseCaseTest {

  private lateinit var characterRepository: CharacterRepository
  private lateinit var useCase: GenerateFeaturesUseCase

  private val testFighter = CharacterClass(
    id = "fighter",
    name = "Fighter",
    description = "A master of martial combat, skilled with a variety of weapons and armor",
    hitDice = "1d10",
    primaryAbility = listOf("Strength", "Dexterity")
  )

  private val testWizard = CharacterClass(
    id = "wizard",
    name = "Wizard",
    description = "A scholarly magic-user capable of manipulating the structures of reality",
    hitDice = "1d6",
    primaryAbility = listOf("Intelligence")
  )

  @Before
  fun setUp() {
    characterRepository = mockk()
    useCase = GenerateFeaturesUseCase(characterRepository)
  }

  @Test
  fun `should generate features for given character class`() {
    val expectedFeatures = Feature.getFeaturesForClass(testFighter, 1)
    every { characterRepository.getFeaturesForClass(testFighter, 1) } returns expectedFeatures

    val features = useCase.generate(testFighter)

    assertNotNull("Features should not be null", features)
    assertTrue("Features list should not be empty", features.isNotEmpty())
    verify { characterRepository.getFeaturesForClass(testFighter, 1) }
  }

  @Test
  fun `generated features should have valid properties`() {
    val expectedFeatures = Feature.getFeaturesForClass(testFighter, 1)
    every { characterRepository.getFeaturesForClass(testFighter, 1) } returns expectedFeatures

    val features = useCase.generate(testFighter)

    features.forEach { feature ->
      assertNotNull("Feature should not be null", feature)
      assertTrue("Feature name should not be blank", feature.name.isNotBlank())
      assertTrue("Feature description should not be blank", feature.description.isNotBlank())
      assertTrue("Feature id should not be blank", feature.id.isNotBlank())
      assertEquals("Feature level should be 1", 1, feature.level)
    }
  }

  @Test
  fun `should generate correct number of features for Fighter`() {
    val expectedFeatures = Feature.getFeaturesForClass(testFighter, 1)
    every { characterRepository.getFeaturesForClass(testFighter, 1) } returns expectedFeatures

    val features = useCase.generate(testFighter)

    assertEquals("Fighter should have 2 features at level 1", 2, features.size)
  }

  @Test
  fun `should generate correct number of features for Wizard`() {
    val expectedFeatures = Feature.getFeaturesForClass(testWizard, 1)
    every { characterRepository.getFeaturesForClass(testWizard, 1) } returns expectedFeatures

    val features = useCase.generate(testWizard)

    assertEquals("Wizard should have 2 features at level 1", 2, features.size)
  }

  @Test
  fun `should generate Fighter specific features`() {
    val expectedFeatures = Feature.getFeaturesForClass(testFighter, 1)
    every { characterRepository.getFeaturesForClass(testFighter, 1) } returns expectedFeatures

    val features = useCase.generate(testFighter)

    val featureNames = features.map { it.name }
    assertTrue("Fighter should have Fighting Style", featureNames.contains("Fighting Style"))
    assertTrue("Fighter should have Second Wind", featureNames.contains("Second Wind"))
  }

  @Test
  fun `should generate Wizard specific features`() {
    val expectedFeatures = Feature.getFeaturesForClass(testWizard, 1)
    every { characterRepository.getFeaturesForClass(testWizard, 1) } returns expectedFeatures

    val features = useCase.generate(testWizard)

    val featureNames = features.map { it.name }
    assertTrue("Wizard should have Spellcasting", featureNames.contains("Spellcasting"))
    assertTrue("Wizard should have Arcane Recovery", featureNames.contains("Arcane Recovery"))
  }

  @Test
  fun `should generate features for all available classes`() {
    CharacterClass.AVAILABLE_CLASSES.forEach { characterClass ->
      val expectedFeatures = Feature.getFeaturesForClass(characterClass, 1)
      every { characterRepository.getFeaturesForClass(characterClass, 1) } returns expectedFeatures

      val features = useCase.generate(characterClass)

      assertNotNull("Features for ${characterClass.name} should not be null", features)
      assertTrue(
        "Features for ${characterClass.name} should not be empty",
        features.isNotEmpty()
      )
      assertTrue(
        "All features for ${characterClass.name} should be level 1",
        features.all { it.level == 1 }
      )
    }
  }

  @Test
  fun `generated features should have unique ids`() {
    val expectedFeatures = Feature.getFeaturesForClass(testFighter, 1)
    every { characterRepository.getFeaturesForClass(testFighter, 1) } returns expectedFeatures

    val features = useCase.generate(testFighter)

    val featureIds = features.map { it.id }
    val uniqueIds = featureIds.toSet()
    assertEquals(
      "All feature IDs should be unique",
      featureIds.size,
      uniqueIds.size
    )
  }
}

