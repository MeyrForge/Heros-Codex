package com.meyrforge.heroscodex.feature_name_generator.domain.usecase

import com.meyrforge.heroscodex.feature_name_generator.domain.model.Gender
import com.meyrforge.heroscodex.feature_name_generator.domain.repository.NameRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class GenerateNameUseCaseTest {

  private lateinit var nameRepository: NameRepository
  private lateinit var useCase: GenerateNameUseCase

  private val maleEndings = listOf("on", "ar", "us", "or", "an", "er", "ix")
  private val femaleEndings = listOf("ia", "ra", "el", "na", "la", "sa", "yn")
  private val nameStarts = listOf("Aer", "Bel", "Cel", "Dar")

  @Before
  fun setUp() {
    nameRepository = mockk()
    useCase = GenerateNameUseCase(nameRepository)

    every { nameRepository.getMaleEndings() } returns maleEndings
    every { nameRepository.getFemaleEndings() } returns femaleEndings
    every { nameRepository.getNameStarts() } returns nameStarts
  }

  @Test
  fun `should generate valid male name with correct structure`() {
    val name = useCase.generate(Gender.MALE)

    assertNotNull("Name should not be null", name)
    assertTrue("Name should not be blank", name.value.isNotBlank())
    assertTrue("Name should end with male ending", maleEndings.any { name.value.endsWith(it) })
    assertTrue("Name should start with valid prefix", nameStarts.any { name.value.startsWith(it) })
  }

  @Test
  fun `should generate valid female name with correct structure`() {
    val name = useCase.generate(Gender.FEMALE)

    assertNotNull("Name should not be null", name)
    assertTrue("Name should not be blank", name.value.isNotBlank())
    assertTrue("Name should end with female ending", femaleEndings.any { name.value.endsWith(it) })
    assertTrue("Name should start with valid prefix", nameStarts.any { name.value.startsWith(it) })
  }

  @Test
  fun `should generate valid neutral name with either male or female ending`() {
    val name = useCase.generate(Gender.NEUTRAL)

    assertNotNull("Name should not be null", name)
    assertTrue("Name should not be blank", name.value.isNotBlank())
    assertTrue(
      "Name should end with male or female ending",
      (maleEndings + femaleEndings).any { name.value.endsWith(it) }
    )
    assertTrue("Name should start with valid prefix", nameStarts.any { name.value.startsWith(it) })
  }

  @Test
  fun `should call repository for male name generation`() {
    useCase.generate(Gender.MALE)

    verify { nameRepository.getMaleEndings() }
    verify { nameRepository.getNameStarts() }
  }

  @Test
  fun `should call repository for female name generation`() {
    useCase.generate(Gender.FEMALE)

    verify { nameRepository.getFemaleEndings() }
    verify { nameRepository.getNameStarts() }
  }

  @Test
  fun `should generate variety in names on multiple calls`() {
    val names = mutableSetOf<String>()

    repeat(10) {
      val name = useCase.generate(Gender.MALE)
      names.add(name.value)
    }

    assertTrue("Should generate some variety in names", names.size > 1)
  }
}
