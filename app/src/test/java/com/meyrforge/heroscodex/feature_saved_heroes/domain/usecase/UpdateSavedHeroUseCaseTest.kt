package com.meyrforge.heroscodex.feature_saved_heroes.domain.usecase

import com.meyrforge.heroscodex.core.domain.model.Background
import com.meyrforge.heroscodex.core.domain.model.Gender
import com.meyrforge.heroscodex.core.domain.model.Race
import com.meyrforge.heroscodex.core.domain.model.SavedHero
import com.meyrforge.heroscodex.core.domain.repository.SavedHeroesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.UUID

class UpdateSavedHeroUseCaseTest {

    private lateinit var repository: SavedHeroesRepository
    private lateinit var updateSavedHeroUseCase: UpdateSavedHeroUseCase

    @Before
    fun setUp() {
        repository = mockk()
        updateSavedHeroUseCase = UpdateSavedHeroUseCase(repository)
    }
    @Test
    fun `editing traits of existing hero updates stored hero`() = runTest {
        val heroId = UUID.randomUUID()

        val original = SavedHero(
            id = heroId,
            name = "Thorin",
            race = Race.DWARF,
            gender = Gender.MALE,
            background = Background.SOLDIER,
            createdAt = System.currentTimeMillis()
        )

        val updated = original.copy(
            name = "Thorin Oakenshield",
            background = Background.NOBLE
        )

        coEvery { repository.getSavedHeroes() } returnsMany listOf(
            Result.success(listOf(original)),
            Result.success(listOf(updated))
        )
        coEvery { repository.updateSavedHero(updated) } returns Result.success(Unit)

        val before = repository.getSavedHeroes()
        assertTrue(before.isSuccess)
        assertEquals("Thorin", before.getOrNull()?.firstOrNull()?.name)

        val result = updateSavedHeroUseCase(updated)
        assertTrue(result.isSuccess)
        coVerify { repository.updateSavedHero(updated) }

        val after = repository.getSavedHeroes()
        assertTrue(after.isSuccess)
        assertEquals("Thorin Oakenshield", after.getOrNull()?.firstOrNull()?.name)
        assertEquals(Background.NOBLE, after.getOrNull()?.firstOrNull()?.background)
    }

    @Test
    fun `editing traits fails when no tokens available and stored hero unchanged`() = runTest {
        val heroId = UUID.randomUUID()

        val original = SavedHero(
            id = heroId,
            name = "Borin",
            race = Race.HALFLING,
            gender = Gender.MALE,
            background = Background.FOLK_HERO,
            createdAt = System.currentTimeMillis()
        )

        val updated = original.copy(name = "Borin the Brave")

        val exception = IllegalStateException("No tokens available")
        coEvery { repository.updateSavedHero(updated) } returns Result.failure(exception)
        coEvery { repository.getSavedHeroes() } returnsMany listOf(
            Result.success(listOf(original)),
            Result.success(listOf(original))
        )

        val before = repository.getSavedHeroes()
        assertTrue(before.isSuccess)
        assertEquals("Borin", before.getOrNull()?.firstOrNull()?.name)

        val result = updateSavedHeroUseCase(updated)
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify { repository.updateSavedHero(updated) }

        val after = repository.getSavedHeroes()
        assertTrue(after.isSuccess)
        assertEquals("Borin", after.getOrNull()?.firstOrNull()?.name)
    }

    @Test
    fun `generating new name for existing hero updates stored hero name`() = runTest {
        val heroId = UUID.randomUUID()

        val original = SavedHero(
            id = heroId,
            name = "Lira",
            race = Race.ELF,
            gender = Gender.FEMALE,
            background = Background.SAGE,
            createdAt = System.currentTimeMillis()
        )

        val updated = original.copy(name = "Lira Silverleaf")

        coEvery { repository.getSavedHeroes() } returnsMany listOf(
            Result.success(listOf(original)),
            Result.success(listOf(updated))
        )
        coEvery { repository.updateSavedHero(updated) } returns Result.success(Unit)

        val before = repository.getSavedHeroes()
        assertTrue(before.isSuccess)
        assertEquals("Lira", before.getOrNull()?.firstOrNull()?.name)

        val result = updateSavedHeroUseCase(updated)
        assertTrue(result.isSuccess)
        coVerify { repository.updateSavedHero(updated) }

        val after = repository.getSavedHeroes()
        assertTrue(after.isSuccess)
        assertEquals("Lira Silverleaf", after.getOrNull()?.firstOrNull()?.name)
    }

    @Test
    fun `generating new name fails when no tokens available and stored name unchanged`() = runTest {
        val heroId = UUID.randomUUID()

        val original = SavedHero(
            id = heroId,
            name = "Mara",
            race = Race.HUMAN,
            gender = Gender.FEMALE,
            background = Background.SAILOR,
            createdAt = System.currentTimeMillis()
        )

        val updated = original.copy(name = "Mara Storm")

        val exception = IllegalStateException("No tokens available")
        coEvery { repository.updateSavedHero(updated) } returns Result.failure(exception)
        coEvery { repository.getSavedHeroes() } returnsMany listOf(
            Result.success(listOf(original)),
            Result.success(listOf(original))
        )

        val before = repository.getSavedHeroes()
        assertTrue(before.isSuccess)
        assertEquals("Mara", before.getOrNull()?.firstOrNull()?.name)

        val result = updateSavedHeroUseCase(updated)
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify { repository.updateSavedHero(updated) }

        val after = repository.getSavedHeroes()
        assertTrue(after.isSuccess)
        assertEquals("Mara", after.getOrNull()?.firstOrNull()?.name)
    }
}
