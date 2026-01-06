package com.meyrforge.heroscodex.feature_saved_heroes.domain.usecase

import com.meyrforge.heroscodex.core.domain.repository.SavedHeroesRepository
import com.meyrforge.heroscodex.core.domain.model.Background
import com.meyrforge.heroscodex.core.domain.model.Gender
import com.meyrforge.heroscodex.core.domain.model.Race
import com.meyrforge.heroscodex.core.domain.model.SavedHero
import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.UUID

class DeleteSavedHeroUseCaseTest {

    private val repository: SavedHeroesRepository = mockk()
    private val deleteSavedHeroUseCase = DeleteSavedHeroUseCase(repository)

    @Test
    fun `save a hero then delete it results in no saved heroes`() = runTest {
        val heroId = UUID.randomUUID()
        val savedHero = SavedHero(
            id = heroId,
            name = "Test",
            race = Race.HUMAN,
            gender = Gender.NEUTRAL,
            background = Background.SAGE,
            createdAt = System.currentTimeMillis()
        )

        val heroName = HeroName(
            name = "Test",
            race = Race.HUMAN,
            gender = Gender.NEUTRAL,
            background = Background.SAGE
        )

        coEvery { repository.saveHero(heroName) } returns Result.success(Unit)
        coEvery { repository.deleteSavedHero(heroId) } returns Result.success(Unit)
        coEvery { repository.getSavedHeroes() } returnsMany listOf(
            Result.success(listOf(savedHero)),
            Result.success(emptyList())
        )

        val saveResult = repository.saveHero(heroName)
        assertTrue(saveResult.isSuccess)
        coVerify { repository.saveHero(heroName) }

        // after saving, repository should return the saved hero
        val getResultAfterSave = repository.getSavedHeroes()
        assertTrue(getResultAfterSave.isSuccess)
        assertTrue(getResultAfterSave.getOrNull()?.isNotEmpty() == true)

        val deleteResult = deleteSavedHeroUseCase(heroId)
        coVerify { repository.deleteSavedHero(heroId) }
        assertTrue(deleteResult.isSuccess)

        val getResultAfterDelete = repository.getSavedHeroes()
        assertTrue(getResultAfterDelete.isSuccess)
        assertTrue(getResultAfterDelete.getOrNull()?.isEmpty() == true)
    }

    @Test
    fun `deleting a non existing hero returns failure`() = runTest {
        val heroId = UUID.randomUUID()
        val exception = NoSuchElementException("Hero not found")
        coEvery { repository.deleteSavedHero(heroId) } returns Result.failure(exception)

        val result = deleteSavedHeroUseCase(heroId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify { repository.deleteSavedHero(heroId) }
    }

    @Test
    fun `saving the same hero twice with same id fails on second save`() = runTest {
        val heroName = HeroName(
            name = "Duplicate",
            race = Race.HUMAN,
            gender = Gender.NEUTRAL,
            background = Background.SAGE
        )

        coEvery { repository.saveHero(heroName) } returnsMany listOf(
            Result.success(Unit),
            Result.failure(IllegalStateException("Duplicate id"))
        )

        val first = repository.saveHero(heroName)
        assertTrue(first.isSuccess)

        val second = repository.saveHero(heroName)
        assertTrue(second.isFailure)
        assertTrue(second.exceptionOrNull() is IllegalStateException)

        coVerify(exactly = 2) { repository.saveHero(heroName) }
    }
}
