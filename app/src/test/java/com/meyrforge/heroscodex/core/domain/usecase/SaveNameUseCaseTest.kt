package com.meyrforge.heroscodex.core.domain.usecase

import com.meyrforge.heroscodex.core.domain.model.Background
import com.meyrforge.heroscodex.core.domain.model.Gender
import com.meyrforge.heroscodex.core.domain.model.Race
import com.meyrforge.heroscodex.core.domain.repository.SavedHeroesRepository
import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SaveNameUseCaseTest {

  private lateinit var useCase: SaveNameUseCase
  private lateinit var savedHeroesRepository: SavedHeroesRepository

  @Before
  fun setUp() {
    savedHeroesRepository = mockk()
    useCase = SaveNameUseCase(savedHeroesRepository)
  }

  @Test
  fun `invoke with hero name saves the name`() = runBlocking {
    val heroName = HeroName(
      name = "Eldrin",
      race = Race.ELF,
      gender = Gender.MALE,
      background = Background.SAGE
    )
    coEvery { savedHeroesRepository.saveHero(heroName) } returns Result.success(Unit)

    val result = useCase(heroName)

    coVerify { savedHeroesRepository.saveHero(heroName) }
    assertTrue(result.isSuccess)
  }

  @Test
  fun `invoke with hero name returns failure if repository fails`() = runBlocking {
    val heroName = HeroName(
      name = "Eldrin",
      race = Race.ELF,
      gender = Gender.MALE,
      background = Background.SAGE
    )
    val exception = RuntimeException("Failed to save hero")
    coEvery { savedHeroesRepository.saveHero(heroName) } returns Result.failure(exception)

    val result = useCase(heroName)

    assertTrue(result.isFailure)
    assertTrue(result.exceptionOrNull() is RuntimeException)
  }
}
