package com.meyrforge.heroscodex.core.data.repository

import com.meyrforge.heroscodex.core.database.dao.SavedTokensDao
import com.meyrforge.heroscodex.core.database.entity.TokensEntity
import com.meyrforge.heroscodex.core.domain.repository.TokensRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val REGEN_INTERVAL_MS: Long = 15 * 60 * 1000L // 15 minutes

class TokensRepositoryImpl @Inject constructor(
  private val savedTokensDao: SavedTokensDao
) : TokensRepository {

  private suspend fun applyRegenerationIfNeeded(entity: TokensEntity?): TokensEntity {
    val now = System.currentTimeMillis()
    if (entity == null) {
      val default = TokensEntity(current = 10, max = 10, lastConsumedAt = 0, id = 1)
      savedTokensDao.insert(default)
      return default
    }

    if (entity.current >= entity.max) return entity

    val elapsed = now - entity.lastConsumedAt
    if (entity.lastConsumedAt <= 0 || elapsed < REGEN_INTERVAL_MS) return entity

    val increments = (elapsed / REGEN_INTERVAL_MS).toInt()
    if (increments <= 0) return entity

    val newCurrent = (entity.current + increments).coerceAtMost(entity.max)
    val usedIncrements = newCurrent - entity.current
    val newLast = if (newCurrent >= entity.max) now else entity.lastConsumedAt + usedIncrements * REGEN_INTERVAL_MS

    val updated = TokensEntity(id = entity.id, current = newCurrent, max = entity.max, lastConsumedAt = newLast)
    savedTokensDao.insert(updated)
    return updated
  }

  override fun currentTokensFlow(): Flow<Int> =
    savedTokensDao.getTokensEntity().transformLatest { entity ->
      val updated = applyRegenerationIfNeeded(entity)
      emit(updated.current)
    }

  override fun maxTokensFlow(): Flow<Int> =
    savedTokensDao.getTokensEntity().transformLatest { entity ->
      val updated = applyRegenerationIfNeeded(entity)
      emit(updated.max)
    }

  override fun nextRegenRemainingFlow(): Flow<Long> = kotlinx.coroutines.flow.flow {
    while (true) {
      try {
        val entity = savedTokensDao.getTokensEntity().first()
        val updated = applyRegenerationIfNeeded(entity)

        if (updated.current >= updated.max) {
          emit(-1L)
          kotlinx.coroutines.delay(1000L)
          continue
        }

        val last = if (updated.lastConsumedAt <= 0L) System.currentTimeMillis() else updated.lastConsumedAt
        val remaining = (last + REGEN_INTERVAL_MS) - System.currentTimeMillis()
        emit(remaining.coerceAtLeast(0L))
        kotlinx.coroutines.delay(1000L)
      } catch (e: Exception) {
        // on error, wait briefly and retry
        kotlinx.coroutines.delay(1000L)
      }
    }
  }

  override suspend fun consume(amount: Int): Result<Boolean> = withContext(Dispatchers.IO) {
    try {
      val entity = savedTokensDao.getTokensEntity().first()
      val updated = applyRegenerationIfNeeded(entity)

      return@withContext if (updated.current >= amount) {
        val newCurrent = updated.current - amount
        // Only start the countdown if tokens were at max before this consumption.
        // If a countdown is already running (updated.lastConsumedAt > 0) keep it.
        val newLast = if (updated.current >= updated.max && newCurrent < updated.max) System.currentTimeMillis() else updated.lastConsumedAt
        savedTokensDao.insert(TokensEntity(id = updated.id, current = newCurrent, max = updated.max, lastConsumedAt = newLast))
        Result.success(true)
      } else {
        Result.success(false)
      }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun add(amount: Int): Result<Unit> = withContext(Dispatchers.IO) {
    try {
      val entity = savedTokensDao.getTokensEntity().first()
      val updated = applyRegenerationIfNeeded(entity)
      val newCurrent = (updated.current + amount).coerceAtMost(updated.max)
      val newLast = if (newCurrent >= updated.max) System.currentTimeMillis() else updated.lastConsumedAt
      savedTokensDao.insert(TokensEntity(id = updated.id, current = newCurrent, max = updated.max, lastConsumedAt = newLast))
      Result.success(Unit)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun setMax(max: Int): Result<Unit> = withContext(Dispatchers.IO) {
    try {
      val entity = savedTokensDao.getTokensEntity().first()
      val curr = entity?.current ?: 0
      val newCurrent = curr.coerceAtMost(max)
      val last = entity?.lastConsumedAt ?: 0L
      savedTokensDao.insert(TokensEntity(id = entity?.id ?: 1, current = newCurrent, max = max, lastConsumedAt = last))
      Result.success(Unit)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun setCurrent(value: Int): Result<Unit> = withContext(Dispatchers.IO) {
    try {
      val entity = savedTokensDao.getTokensEntity().first()
      val max = entity?.max ?: 10
      val newCurrent = value.coerceAtMost(max)
      val newLast = if (newCurrent < max) System.currentTimeMillis() else (entity?.lastConsumedAt ?: 0L)
      savedTokensDao.insert(TokensEntity(id = entity?.id ?: 1, current = newCurrent, max = max, lastConsumedAt = newLast))
      Result.success(Unit)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }
}
