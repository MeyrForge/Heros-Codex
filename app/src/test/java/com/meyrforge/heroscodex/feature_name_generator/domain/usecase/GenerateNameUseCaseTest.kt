package com.meyrforge.heroscodex.feature_name_generator.domain.usecase

import com.meyrforge.heroscodex.feature_name_generator.domain.model.Background
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Gender
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Race
import com.meyrforge.heroscodex.feature_name_generator.domain.repository.NameRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class GenerateNameUseCaseTest {

  private lateinit var nameRepository: NameRepository
  private lateinit var useCase: GenerateNameUseCase

  private val maleEndings = listOf("on", "ar", "us", "or", "an", "er", "ix")
  private val femaleEndings = listOf("ia", "ra", "el", "na", "la", "sa", "yn")
  private val nameStarts = listOf("Aer", "Bel", "Cel", "Dar")
  private val suffixes = listOf("the Devoted", "the Faithful", "the Blessed")

  @Before
  fun setUp() {
    nameRepository = mockk()
    useCase = GenerateNameUseCase(nameRepository)

    every { nameRepository.getMaleEndings(any()) } returns maleEndings
    every { nameRepository.getFemaleEndings(any()) } returns femaleEndings
    every { nameRepository.getNameStarts(any()) } returns nameStarts
    every { nameRepository.getSuffixes(any()) } returns suffixes
  }

  @Test
  fun `should generate valid male name with correct structure`() {
    val name = useCase.generate(Gender.MALE, Race.HUMAN, Background.ACOLYTE)

    assertValidNameStructure(name.name, maleEndings)
  }

  @Test
  fun `should generate valid female name with correct structure`() {
    val name = useCase.generate(Gender.FEMALE, Race.HUMAN, Background.ACOLYTE)

    assertValidNameStructure(name.name, femaleEndings)
  }

  @Test
  fun `should generate valid neutral name with either male or female ending`() {
    val name = useCase.generate(Gender.NEUTRAL, Race.HUMAN, Background.ACOLYTE)

    assertValidNameStructure(name.name, maleEndings + femaleEndings)
  }

  @Test
  fun `should generate variety in names on multiple calls`() {
    val names = mutableSetOf<String>()

    repeat(10) {
      val name = useCase.generate(Gender.MALE, Race.HUMAN, Background.ACOLYTE)
      names.add(name.name)
    }

    assertTrue("Should generate some variety in names", names.size > 1)
  }

  private fun assertValidNameStructure(nameValue: String, validEndings: List<String>) {
    assertTrue("Name should not be blank", nameValue.isNotBlank())
    assertTrue(
      "Name should end with valid suffix",
      suffixes.any { nameValue.endsWith(it) }
    )
    assertTrue(
      "Name should contain valid ending",
      validEndings.any { ending -> nameValue.contains(ending) }
    )
    assertTrue(
      "Name should start with valid prefix",
      nameStarts.any { nameValue.startsWith(it) }
    )
  }
}
