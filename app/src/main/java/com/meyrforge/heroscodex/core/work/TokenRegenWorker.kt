package com.meyrforge.heroscodex.core.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.meyrforge.heroscodex.core.database.dao.SavedTokensDao
import com.meyrforge.heroscodex.core.database.entity.TokensEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

private const val REGEN_INTERVAL_MS: Long = 15 * 60 * 1000L // 15 minutes

@HiltWorker
class TokenRegenWorker @AssistedInject constructor(
  @Assisted context: Context,
  @Assisted params: WorkerParameters,
  private val savedTokensDao: SavedTokensDao
) : CoroutineWorker(context, params) {

  override suspend fun doWork(): Result {
    try {
      val dao = savedTokensDao

      val entity = dao.getTokensEntity().first()
      val now = System.currentTimeMillis()

      if (entity == null) {
        dao.insert(TokensEntity(id = 1, current = 10, max = 10, lastConsumedAt = 0))
        return Result.success()
      }

      if (entity.current >= entity.max) return Result.success()

      val elapsed = now - entity.lastConsumedAt
      if (entity.lastConsumedAt <= 0 || elapsed < REGEN_INTERVAL_MS) return Result.success()

      val increments = (elapsed / REGEN_INTERVAL_MS).toInt()
      if (increments <= 0) return Result.success()

      val newCurrent = (entity.current + increments).coerceAtMost(entity.max)
      val usedIncrements = newCurrent - entity.current
      val newLast = if (newCurrent >= entity.max) now else entity.lastConsumedAt + usedIncrements * REGEN_INTERVAL_MS

      dao.insert(TokensEntity(id = entity.id, current = newCurrent, max = entity.max, lastConsumedAt = newLast))

      return Result.success()
    } catch (e: Exception) {
      return Result.retry()
    }
  }
}
