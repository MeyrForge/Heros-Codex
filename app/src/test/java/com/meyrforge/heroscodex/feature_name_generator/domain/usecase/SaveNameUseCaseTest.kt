package com.meyrforge.heroscodex.feature_name_generator.domain.usecase

import com.meyrforge.heroscodex.feature_name_generator.domain.model.Background
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Gender
import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Race
import com.meyrforge.heroscodex.feature_name_generator.domain.repository.NameRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SaveNameUseCaseTest {

    private lateinit var nameRepository: NameRepository
    private lateinit var useCase: SaveNameUseCase

    @Before
    fun setUp() {
        nameRepository = mockk()
        useCase = SaveNameUseCase(nameRepository)
    }

    @Test
    fun `should return success when repository saves name`() = runTest {
        val heroName = HeroName(
            name = "Kael",
            race = Race.HUMAN,
            gender = Gender.MALE,
            background = Background.SOLDIER
        )
        coEvery { nameRepository.saveName(heroName) } returns Result.success(Unit)

        val result = useCase(heroName)

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { nameRepository.saveName(heroName) }
    }

    @Test
    fun `should return failure when repository throws exception`() = runTest {
        val heroName = HeroName(
            name = "Kael",
            race = Race.HUMAN,
            gender = Gender.MALE,
            background = Background.SOLDIER
        )
        val exception = Exception("Failed to save name")
        coEvery { nameRepository.saveName(heroName) } returns Result.failure(exception)

        val result = useCase(heroName)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { nameRepository.saveName(heroName) }
    }
}
