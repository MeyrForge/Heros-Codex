package com.meyrforge.heroscodex.feature_saved_heroes.domain.usecase

import com.meyrforge.heroscodex.core.domain.model.Background
import com.meyrforge.heroscodex.core.domain.model.Gender
import com.meyrforge.heroscodex.core.domain.model.Race
import com.meyrforge.heroscodex.core.domain.model.SavedHero
import com.meyrforge.heroscodex.core.domain.repository.SavedHeroesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.UUID

class GetSavedHeroesUseCaseTest {

    private val repository: SavedHeroesRepository = mockk()
    private val getSavedHeroesUseCase = GetSavedHeroesUseCase(repository)

    @Test
    fun `invoke with successful repository response returns hero list`() = runTest {
        val heroList = listOf(
            SavedHero(
                id = UUID.randomUUID(),
                name = "Eldrin",
                race = Race.ELF,
                gender = Gender.MALE,
                background = Background.SAGE,
                createdAt = System.currentTimeMillis()
            )
        )
        coEvery { repository.getSavedHeroes() } returns Result.success(heroList)

        val result = getSavedHeroesUseCase()

        assertEquals(heroList, result.getOrNull())
    }

    @Test
    fun `invoke with repository failure returns failure`() = runTest {
        val exception = RuntimeException("Database error")
        coEvery { repository.getSavedHeroes() } returns Result.failure(exception)

        val result = getSavedHeroesUseCase()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
