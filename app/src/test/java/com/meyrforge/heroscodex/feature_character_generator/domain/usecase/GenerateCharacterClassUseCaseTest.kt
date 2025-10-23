package com.meyrforge.heroscodex.feature_character_generator.domain.usecase

import com.meyrforge.heroscodex.feature_character_generator.domain.model.CharacterClass
import com.meyrforge.heroscodex.feature_character_generator.domain.repository.CharacterRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class GenerateCharacterClassUseCaseTest {

  private lateinit var characterRepository: CharacterRepository
  private lateinit var useCase: GenerateCharacterClassUseCase

  @Before
  fun setUp() {
    characterRepository = mockk()
    useCase = GenerateCharacterClassUseCase(characterRepository)

    every { characterRepository.getAvailableClasses() } returns CharacterClass.AVAILABLE_CLASSES
  }

  @Test
  fun `should generate valid character class from available classes`() {
    val characterClass = useCase.generate()

    assertNotNull("Character class should not be null", characterClass)
    assertTrue(
      "Generated class should be from available classes",
      CharacterClass.AVAILABLE_CLASSES.any { it.id == characterClass.id }
    )
  }

  @Test
  fun `generated class should have all required properties`() {
    val characterClass = useCase.generate()

    assertTrue("Class name should not be blank", characterClass.name.isNotBlank())
    assertTrue("Class description should not be blank", characterClass.description.isNotBlank())
    assertTrue("Hit dice should not be blank", characterClass.hitDice.isNotBlank())
    assertTrue(
      "Primary ability list should not be empty",
      characterClass.primaryAbility.isNotEmpty()
    )
    characterClass.primaryAbility.forEach { ability ->
      assertTrue("Primary ability should not be blank", ability.isNotBlank())
    }
  }

  @Test
  fun `generated class should have correct hit dice format`() {
    val characterClass = useCase.generate()

    val hitDicePattern = Regex("1d\\d+")
    assertTrue("Hit dice should match format 1dX", hitDicePattern.matches(characterClass.hitDice))
  }

  @Test
  fun `should generate variety of classes on multiple calls`() {
    val generatedClasses = mutableSetOf<String>()

    repeat(20) {
      val characterClass = useCase.generate()
      generatedClasses.add(characterClass.id)
    }

    assertTrue(
      "Should generate some variety in classes (at least 2 different)",
      generatedClasses.size >= 2
    )
  }

  @Test
  fun `should call repository when generating class`() {
    useCase.generate()

    verify { characterRepository.getAvailableClasses() }
  }
}

