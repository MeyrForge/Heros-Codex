package com.meyrforge.heroscodex.core.data.repository

import com.meyrforge.heroscodex.core.database.dao.SavedTokensDao
import com.meyrforge.heroscodex.core.database.entity.TokensEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TokensRepositoryImplTest {

  private lateinit var savedTokensDao: SavedTokensDao
  private lateinit var repository: TokensRepositoryImpl

  @Before
  fun setUp() {
    savedTokensDao = mockk()
    repository = TokensRepositoryImpl(savedTokensDao)
  }

  @Test
  fun `currentTokensFlow emits default when entity is null`() = runBlocking {
    every { savedTokensDao.getTokensEntity() } returns flowOf(null)
    coEvery { savedTokensDao.insert(any()) } returns Unit

    val value = repository.currentTokensFlow().first()

    assertEquals(10, value)
  }

  @Test
  fun `consume reduces tokens and starts countdown when coming from max`() = runBlocking {
    val entity = TokensEntity(id = 1, current = 10, max = 10, lastConsumedAt = 0L)
    every { savedTokensDao.getTokensEntity() } returns flowOf(entity)
    coEvery { savedTokensDao.insert(any()) } returns Unit

    val result = repository.consume(1)

    assertTrue(result.isSuccess)
    coVerify { savedTokensDao.insert(match { it.current == 9 && it.max == 10 && it.lastConsumedAt > 0L }) }
  }

  @Test
  fun `consume fails when not enough tokens`() = runBlocking {
    val entity = TokensEntity(id = 1, current = 0, max = 10, lastConsumedAt = 0L)
    every { savedTokensDao.getTokensEntity() } returns flowOf(entity)
    coEvery { savedTokensDao.insert(any()) } returns Unit

    val result = repository.consume(1)

    assertTrue(result.isSuccess)
    assertEquals(false, result.getOrNull())
    coVerify(exactly = 0) { savedTokensDao.insert(any()) }
  }

  @Test
  fun `add clamps to max`() = runBlocking {
    val entity = TokensEntity(id = 1, current = 8, max = 10, lastConsumedAt = 0L)
    every { savedTokensDao.getTokensEntity() } returns flowOf(entity)
    coEvery { savedTokensDao.insert(any()) } returns Unit

    val result = repository.add(5)

    assertTrue(result.isSuccess)
    coVerify { savedTokensDao.insert(match { it.current == 10 && it.max == 10 }) }
  }

  @Test
  fun `setMax reduces current when greater than new max`() = runBlocking {
    val entity = TokensEntity(id = 1, current = 10, max = 15, lastConsumedAt = 0L)
    every { savedTokensDao.getTokensEntity() } returns flowOf(entity)
    coEvery { savedTokensDao.insert(any()) } returns Unit

    val result = repository.setMax(5)

    assertTrue(result.isSuccess)
    coVerify { savedTokensDao.insert(match { it.current == 5 && it.max == 5 }) }
  }
}
