package com.meyrforge.heroscodex.feature_name_generator.domain.usecase

import com.meyrforge.heroscodex.core.domain.model.Background
import com.meyrforge.heroscodex.core.domain.model.Gender
import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import com.meyrforge.heroscodex.core.domain.model.Race
import com.meyrforge.heroscodex.feature_name_generator.domain.repository.NameRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class GenerateNameUseCaseTest {

  private lateinit var nameRepository: NameRepository
  private lateinit var useCase: GenerateNameUseCase

  @Before
  fun setUp() {
    nameRepository = mockk()
    useCase = GenerateNameUseCase(nameRepository)
  }

  @Test
  fun `should return success when repository returns valid name`() = runTest {
    val expectedHeroName = HeroName(
      name = "Aldric the Devoted",
      race = Race.HUMAN,
      gender = Gender.MALE,
      background = Background.ACOLYTE
    )

    coEvery {
      nameRepository.generateName(Race.HUMAN, Gender.MALE, Background.ACOLYTE)
    } returns Result.success(expectedHeroName)

    val result = useCase(Gender.MALE, Race.HUMAN, Background.ACOLYTE)

    assertTrue(result.isSuccess)
    assertEquals(expectedHeroName, result.getOrNull())
    coVerify { nameRepository.generateName(Race.HUMAN, Gender.MALE, Background.ACOLYTE) }
  }

  @Test
  fun `should return failure when repository fails`() = runTest {
    val exception = Exception("Database error")

    coEvery {
      nameRepository.generateName(Race.ELF, Gender.FEMALE, Background.NOBLE)
    } returns Result.failure(exception)

    val result = useCase(Gender.FEMALE, Race.ELF, Background.NOBLE)

    assertTrue(result.isFailure)
    assertEquals(exception, result.exceptionOrNull())
  }

  @Test
  fun `should generate different names for different races`() = runTest {
    val humanName = HeroName("Marcus", Race.HUMAN, Gender.MALE, Background.SOLDIER)
    val elfName = HeroName("Legolas", Race.ELF, Gender.MALE, Background.SOLDIER)

    coEvery {
      nameRepository.generateName(Race.HUMAN, Gender.MALE, Background.SOLDIER)
    } returns Result.success(humanName)

    coEvery {
      nameRepository.generateName(Race.ELF, Gender.MALE, Background.SOLDIER)
    } returns Result.success(elfName)

    val humanResult = useCase(Gender.MALE, Race.HUMAN, Background.SOLDIER)
    val elfResult = useCase(Gender.MALE, Race.ELF, Background.SOLDIER)

    assertEquals("Marcus", humanResult.getOrNull()?.name)
    assertEquals("Legolas", elfResult.getOrNull()?.name)
  }

  @Test
  fun `should preserve all parameters in generated HeroName`() = runTest {
    val heroName = HeroName(
      name = "Thokk",
      race = Race.HALF_ORC,
      gender = Gender.NEUTRAL,
      background = Background.OUTLANDER
    )

    coEvery {
      nameRepository.generateName(Race.HALF_ORC, Gender.NEUTRAL, Background.OUTLANDER)
    } returns Result.success(heroName)

    val result = useCase(Gender.NEUTRAL, Race.HALF_ORC, Background.OUTLANDER)

    val generatedName = result.getOrNull()
    assertNotNull(generatedName)
    assertEquals(Race.HALF_ORC, generatedName?.race)
    assertEquals(Gender.NEUTRAL, generatedName?.gender)
    assertEquals(Background.OUTLANDER, generatedName?.background)
  }
}
