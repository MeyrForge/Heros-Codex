package com.meyrforge.heroscodex.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokensRepository {
  fun currentTokensFlow(): Flow<Int>
  fun maxTokensFlow(): Flow<Int>
  fun nextRegenRemainingFlow(): Flow<Long>
  suspend fun consume(amount: Int = 1): Result<Boolean>
  suspend fun add(amount: Int): Result<Unit>
  suspend fun setMax(max: Int): Result<Unit>
  suspend fun setCurrent(value: Int): Result<Unit>
}
